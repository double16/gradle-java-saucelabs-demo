package me.pdouble.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonPage {
  WebDriver driver;

  public CommonPage(WebDriver driver) {
    this.driver = driver;
  }

  WebDriverWait slow() {
    return new WebDriverWait(driver, 10);
  }

  void waitForBody() {
    slow().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));
  }

  void waitForTitle(String title) {
    slow().until(ExpectedConditions.titleContains(title));
  }

  public <T> T at(Class<T> page) {
    return PageFactory.initElements(driver, page);
  }
}
