package me.pdouble.pages;

import org.openqa.selenium.WebDriver;

public class AuthenticatedHomePage extends CommonPage {
  public AuthenticatedHomePage(WebDriver driver) {
    super(driver);
    waitForTitle("Home");
  }
}
