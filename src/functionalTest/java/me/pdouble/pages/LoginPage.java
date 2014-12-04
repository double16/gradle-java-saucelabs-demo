package me.pdouble.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * IDEA does not like "SigninPage", not sure why.
 */
public class LoginPage {
  private WebDriver driver;

  @FindBy(name = "j_username")
  WebElement username;
  @FindBy(name = "j_password")
  WebElement password;
  @FindBy(xpath = "//button[@type='submit']")
  WebElement loginButton;
  @FindBy(xpath = "//a[contains(text(),'Sign Up')]")
  WebElement signupButton;

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    if (!driver.getTitle().equals("Sign In")) {
      throw new IllegalStateException("Not the sign in page");
    }
  }

  public SignupPage clickOnSignup() {
    signupButton.click();
    return PageFactory.initElements(driver, SignupPage.class);
  }
}
