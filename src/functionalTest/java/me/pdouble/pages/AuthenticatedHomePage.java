package me.pdouble.pages;

import org.openqa.selenium.WebDriver;

public class AuthenticatedHomePage extends CommonPage {
  public AuthenticatedHomePage(WebDriver driver) {
    super(driver);
    waitForBody();
    if (!driver.getTitle().contains("Home")) {
      throw new IllegalStateException("Not the authenticated home page");
    }
  }
}
