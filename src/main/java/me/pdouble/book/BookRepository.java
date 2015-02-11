package me.pdouble.book;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class BookRepository {
  @PersistenceContext
  private EntityManager entityManager;

  @Transactional(readOnly = true)
  public List<Book> getBooks() {
    return entityManager.createNamedQuery(Book.FIND_ALL).getResultList();
  }

  public void save(Book book) {
    entityManager.persist(book);
  }
}
