package me.pdouble;

import com.github.double16.AbstractFunctionalTest;
import me.pdouble.pages.SignupPage;
import me.pdouble.pages.UnauthenticatedHomePage;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public abstract class AbstractBookFunctionalTest extends AbstractFunctionalTest {
  private static Random newUserNum = new Random();

  @Override
  public String getContextRoot() {
    return "gradle-java-saucelabs-demo/";
  }

  @Before
  public void setUp() throws Exception {
    baseUrl = "http://localhost:8080/"; // FIXME: set this from build
    super.setUp();
  }

  /**
   * Create and authenticate a new user.
   * @return user name.
   */
  public String newUser() {
    String userName = "me"+newUserNum.nextInt(Integer.MAX_VALUE)+"@nowhere.com";
    UnauthenticatedHomePage unauthenticatedHomePage = at(UnauthenticatedHomePage.class);
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
