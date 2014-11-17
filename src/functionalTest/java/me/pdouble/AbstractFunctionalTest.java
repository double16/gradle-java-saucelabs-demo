package me.pdouble;

import me.pdouble.pages.SignupPage;
import me.pdouble.pages.UnauthenticatedHomePage;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractFunctionalTest {
  protected ThreadLocal<NumberFormat> REPORT_OUTPUT_FORMAT = new ThreadLocal<NumberFormat>() {
    @Override
    protected NumberFormat initialValue() {
      return new DecimalFormat("000");
    }
  };
  private static Random newUserNum = new Random();

  protected WebDriver driver;
  protected String baseUrl;
  protected File reportDir;
  protected int reportOutputNum = 1;

  //protected SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

  @Rule
  public TestName testName = new TestName();

  private static String createBrowserSpecSystemPropertyName(int num) {
    if (num < 1) {
      return "saucelabs.browser";
    }
    return "saucelabs.browser."+num;
  }

  private static void fixupPlatform(Map caps) {
    String platform = (String) caps.get(CapabilityType.PLATFORM);
    if (platform != null) {
      Platform platformEnum = null;
      for(Platform p : Platform.values()) {
        for(String osName : p.getPartOfOsName()) {
          if (osName.equalsIgnoreCase(platform)) {
            caps.put(CapabilityType.PLATFORM, p.name());
            return;
          }
        }
      }
    }
  }

  @Parameters
  public static Collection<WebDriver[]> drivers() throws IOException {
    List<WebDriver[]> drivers = new LinkedList<WebDriver[]>();

    int driverSpecNum = 0;
    String spec;
    while ((spec = System.getProperty(createBrowserSpecSystemPropertyName(driverSpecNum++))) != null) {
      Properties browserCaps = new Properties();
      browserCaps.put("name", System.getProperty("saucelabs.job-name", ""));
      browserCaps.put("build", System.getProperty("saucelabs.build", ""));
      browserCaps.load(new StringReader(spec.replaceAll(",", "\n")));
      fixupPlatform((Map) browserCaps);
      DesiredCapabilities capabilities = new DesiredCapabilities((Map) browserCaps);
      WebDriver driver = new RemoteWebDriver(
          new URL("http://" + System.getenv("SAUCE_LABS_USER") + ":" +
              System.getenv("SAUCE_LABS_ACCESS_PASSWORD") + "@ondemand.saucelabs.com:80/wd/hub"),
          capabilities);
      String sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
      System.out.println("SauceOnDemandSessionID=" + sessionId);
      drivers.add(new WebDriver[] { driver });
    }

    if (drivers.isEmpty()) {
      try {
        drivers.add(new WebDriver[] { new PhantomJSDriver() });
      } catch (Exception e) {
        System.err.println("Unable to locate PhantomJS driver");
      }
    }

    return drivers;
  }

  AbstractFunctionalTest(WebDriver driver) {
    this.driver = driver;
  }

  @Before
  public void setUp() throws Exception {
    String testPlatform = driver.getClass().getSimpleName();
    if (driver instanceof RemoteWebDriver) {
      Map<String, Object> caps = new HashMap<String, Object>(((RemoteWebDriver) driver).getCapabilities().asMap());
      StringBuffer sb = new StringBuffer(128);
      sb.append(caps.remove(CapabilityType.BROWSER_NAME));
      sb.append('_');
      sb.append(caps.remove(CapabilityType.PLATFORM));
      sb.append('_');
      sb.append(caps.remove(CapabilityType.VERSION));
      testPlatform = sb.toString();
      /*
      capabilities.setCapability("name", "xxTest :" + testName.getMethodName());

       */
    }
    reportDir = new File(new File(System.getProperty("testReportDir", "build/report/tests")), testPlatform+"/"+testName.getMethodName());
    reportDir.mkdirs();

    baseUrl = "http://localhost:8080/gradle-java-saucelabs-demo/"; // FIXME: set this from build
    driver.get(baseUrl);
  }

  @After
  public void tearDown() {
    report("end");
    driver.close();
    driver.quit();
  }

  public void report(String name) {
    try {
      File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(screenshot, new File(reportDir, REPORT_OUTPUT_FORMAT.get().format(reportOutputNum++)+"-"+name+".png"));
    } catch (IOException e) {
      e.printStackTrace(System.err);
    }
  }

  /**
   * Create and authenticate a new user.
   * @return user name.
   */
  public String newUser() {
    String userName = "me"+newUserNum.nextInt(Integer.MAX_VALUE)+"@nowhere.com";
    UnauthenticatedHomePage unauthenticatedHomePage = PageFactory.initElements(driver, UnauthenticatedHomePage.class);
    report("home-page");
    SignupPage signupPage = unauthenticatedHomePage.clickOnSignup();
    report("pre-signup");
    signupPage.signup(userName, "P@55w0rd");
    report("post-signup");

    WebDriverWait waitVar = new WebDriverWait(driver, 10);
    waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));

    if (driver.findElement(By.cssSelector("body")).getText().toLowerCase().contains("unique")) {
      throw new IllegalStateException("Duplicate user name");
    }

    return userName;
  }
}
