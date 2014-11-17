package me.pdouble;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public abstract class AbstractFunctionalTest {
  protected ThreadLocal<NumberFormat> REPORT_OUTPUT_FORMAT = new ThreadLocal<NumberFormat>() {
    @Override
    protected NumberFormat initialValue() {
      return new DecimalFormat("000");
    }
  };
  protected WebDriver driver;
  protected String baseUrl;
  protected File reportDir;
  protected int reportOutputNum = 1;

  @Rule
  public TestName testName = new TestName();

  @Parameters
  public static Collection<WebDriver[]> drivers() {
    List<WebDriver[]> drivers = new LinkedList<WebDriver[]>();

    /*try {
      drivers.add(new WebDriver[] { new FirefoxDriver() });
    } catch (Exception e) {
      System.err.println("Unable to locate Firefox driver");
    }*/

    try {
      drivers.add(new WebDriver[] { new PhantomJSDriver() });
    } catch (Exception e) {
      System.err.println("Unable to locate PhantomJS driver");
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
}
