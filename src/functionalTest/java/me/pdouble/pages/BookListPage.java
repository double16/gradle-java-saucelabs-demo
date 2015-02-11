package me.pdouble.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BookListPage extends CommonPage {
  public static final String url = "book/list";

  @FindBy(xpath="//a[contains(text(),'New')]")
  WebElement newBookButton;
  @FindBy(css = "table tbody tr")
  List<WebElement> bookList;

  public BookListPage(WebDriver driver) {
    super(driver);
    waitForTitle("Books");
  }

  public NewBookPage clickOnNewBook() {
    newBookButton.click();
    return at(NewBookPage.class);
  }

  public int getDisplayedBookCount() {
    return bookList.size();
  }
}
