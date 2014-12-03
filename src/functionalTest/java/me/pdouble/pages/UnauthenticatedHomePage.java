package me.pdouble.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UnauthenticatedHomePage extends CommonPage {
  @FindBy(xpath = "//a[contains(text(), 'Sign up')]")
  private WebElement signupButton;

  public UnauthenticatedHomePage(WebDriver driver) {
    super(driver);
    waitForBody();
    if (!driver.getTitle().contains("Welcome")) {
      throw new IllegalStateException("Not the unauthenticated home page");
    }
  }

  public SignupPage clickOnSignup() {
    signupButton.click();
    return at(SignupPage.class);
  }
}
