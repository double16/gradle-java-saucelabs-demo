package me.pdouble.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends CommonPage {
  @FindBy(xpath = "//a[contains(text(), 'Sign up')]")
  private WebElement signupButton;

  public HomePage(WebDriver driver) {
    super(driver);
  }

  public SignupPage clickOnSignup() {
    signupButton.click();
    return PageFactory.initElements(driver, SignupPage.class);
  }
}
