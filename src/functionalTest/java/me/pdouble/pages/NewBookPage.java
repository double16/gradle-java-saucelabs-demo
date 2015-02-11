package me.pdouble.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewBookPage extends CommonPage {

  @FindBy(name="title")
  WebElement title;
  @FindBy(name="author")
  WebElement author;
  @FindBy(name="pages")
  WebElement pages;
  @FindBy(xpath="//button[@type='submit']")
  WebElement submit;

  public NewBookPage(WebDriver driver) {
    super(driver);
    waitForTitle("Create Book");
  }

  public BookListPage createBook(String title, String author, int pages) {
    this.title.sendKeys(title);
    this.author.sendKeys(author);
    this.pages.sendKeys(String.valueOf(pages));
    submit.click();
    return at(BookListPage.class);
  }
}
