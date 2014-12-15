package me.pdouble;

import me.pdouble.pages.BookListPage;
import me.pdouble.pages.NewBookPage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class NewBookTest extends AbstractBookFunctionalTest {
  @Test
  public void newBookTest() {
    home();
    newUser();
    BookListPage bookListPage = go(BookListPage.class);
    report("book-list");
    NewBookPage newBookPage = bookListPage.clickOnNewBook();
    report("new-book");
    bookListPage = newBookPage.createBook("The Lord of the Flies", "William Golding", 256);
    Assert.assertTrue("No books shown in the list", bookListPage.getDisplayedBookCount() > 0);
  }
}
