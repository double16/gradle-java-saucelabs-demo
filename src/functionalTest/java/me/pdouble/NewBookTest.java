package me.pdouble;

import me.pdouble.pages.BookListPage;
import me.pdouble.pages.HomePage;
import me.pdouble.pages.NewBookPage;
import me.pdouble.pages.SignupPage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@RunWith(Parameterized.class)
public class NewBookTest extends AbstractFunctionalTest {
  public NewBookTest(WebDriver driver) {
    super(driver);
  }

  @Test
  public void newBookTest() {
    HomePage homePage = PageFactory.initElements(driver, HomePage.class);
    SignupPage signupPage = homePage.clickOnSignup();
    signupPage.signup("me@nowhere.com", "P@55w0rd");
    driver.get(baseUrl+BookListPage.url);
    report("book-list");
    BookListPage bookListPage = PageFactory.initElements(driver, BookListPage.class);
    NewBookPage newBookPage = bookListPage.clickOnNewBook();
    report("new-book");
    bookListPage = newBookPage.createBook("The Lord of the Flies", "William Golding", 256);
    Assert.assertTrue("No books shown in the list", bookListPage.getDisplayedBookCount() > 0);
  }
}
