package me.pdouble.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
    waitForBody();
    if (!driver.getTitle().equals("Create Book")) {
      throw new IllegalStateException("Not the new book page");
    }
  }

  public BookListPage createBook(String title, String author, int pages) {
    this.title.sendKeys(title);
    this.author.sendKeys(author);
    this.pages.sendKeys(String.valueOf(pages));
    submit.click();
    return PageFactory.initElements(driver, BookListPage.class);
  }
}
