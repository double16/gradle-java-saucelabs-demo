package me.pdouble.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BookListPage extends CommonPage {
  public static final String url = "book/list";

  @FindBy(xpath="//a[contains(text(),'New')]")
  WebElement newBookButton;
  @FindBy(css = "table tbody tr")
  List<WebElement> bookList;

  public BookListPage(WebDriver driver) {
    super(driver);
    waitForBody();
    if (!driver.getTitle().equals("Books")) {
      throw new IllegalStateException("Not the book list page");
    }
  }

  public NewBookPage clickOnNewBook() {
    newBookButton.click();
    return PageFactory.initElements(driver, NewBookPage.class);
  }

  public int getDisplayedBookCount() {
    return bookList.size();
  }
}
