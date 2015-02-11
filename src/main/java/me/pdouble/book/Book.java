package me.pdouble.book;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "book")
@NamedQuery(name = Book.FIND_ALL, query = "select b from Book b")
public class Book implements java.io.Serializable {
  public static final String FIND_ALL = "Book.findAll";

  @Id
  @GeneratedValue
  private Long id;
  @NotEmpty
  private String title;
  @NotEmpty
  private String author;
  @Range(min = 1)
  private int pages;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public String toString() {
    return "${title} - ${author}";
  }
}
