package me.pdouble.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Secured("ROLE_USER")
public class BookController {
  private BookRepository bookRepository;

  @Autowired
  public BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @RequestMapping(value = "book/list", method = RequestMethod.GET)
  public ModelMap list(ModelMap model) {
    model.put("books", bookRepository.getBooks());
    return model;
  }

  @RequestMapping(value = "book/create", method = RequestMethod.GET)
  public ModelMap create(ModelMap model) {
    model.put("book", new Book());
    return model;
  }

  @RequestMapping(value = "book/create", method = RequestMethod.POST)
  public String save(@ModelAttribute Book book, BindingResult errors, ModelMap model) {
    if (errors.hasErrors()) {
      model.put("errors", errors.getAllErrors());
      return "book/create";
    }
    bookRepository.save(book);
    return "redirect:list";
  }
}
