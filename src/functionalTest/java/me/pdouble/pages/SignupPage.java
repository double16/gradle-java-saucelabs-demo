package me.pdouble.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignupPage {
  private WebDriver driver;

  @FindBy(id="email")
  WebElement username;
  @FindBy(id="password")
  WebElement password;
  @FindBy(xpath = "//button[@type='submit']")
  WebElement submit;

  public SignupPage(WebDriver driver) {
    this.driver = driver;
    if (!driver.getTitle().equals("Signup")) {
      throw new IllegalStateException("Not the sign in page");
    }
  }

  public void signup(String newUsername, String newPassword) {
    username.sendKeys(newUsername);
    password.sendKeys(newPassword);
    submit.click();
  }
}
