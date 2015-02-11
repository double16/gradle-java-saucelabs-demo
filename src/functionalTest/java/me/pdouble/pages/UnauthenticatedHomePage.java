package me.pdouble.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UnauthenticatedHomePage extends CommonPage {
  @FindBy(xpath = "//a[contains(text(), 'Sign up')]")
  private WebElement signupButton;

  public UnauthenticatedHomePage(WebDriver driver) {
    super(driver);
    waitForTitle("Welcome");
  }

  public SignupPage clickOnSignup() {
    signupButton.click();
    return at(SignupPage.class);
  }
}
