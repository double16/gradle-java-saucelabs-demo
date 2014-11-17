package me.pdouble.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonPage {
  WebDriver driver;

  public CommonPage(WebDriver driver) {
    this.driver = driver;
  }

  void waitForBody() {
    WebDriverWait waitVar = new WebDriverWait(driver, 10);
    waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));
  }
}
