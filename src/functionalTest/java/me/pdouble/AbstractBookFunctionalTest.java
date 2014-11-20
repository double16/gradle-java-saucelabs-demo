package me.pdouble;

import me.pdouble.pages.SignupPage;
import me.pdouble.pages.UnauthenticatedHomePage;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.double16.AbstractFunctionalTest;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public abstract class AbstractBookFunctionalTest extends AbstractFunctionalTest {
  private static Random newUserNum = new Random();

  AbstractBookFunctionalTest(WebDriver driver) {
    super(driver);
  }

  @Before
  public void setUp() throws Exception {
    baseUrl = "http://localhost:8080/gradle-java-saucelabs-demo/"; // FIXME: set this from build
    super.setUp();
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
