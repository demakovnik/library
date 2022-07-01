package ru.demakov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.demakov.library.dao.BookDao;
import ru.demakov.library.dao.PersonDao;
import ru.demakov.library.model.Book;
import ru.demakov.library.model.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    private String getBooks(Model model) {
        model.addAttribute("books", bookDao.getAll());
        return "book/books";
    }

    @GetMapping("/new")
    private String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "book/new";
    }

    @GetMapping("/{id}")
    private String showBook(@PathVariable("id") int id, @ModelAttribute("person") Person person,
                            Model model) {
        Book book = bookDao.getBook(id);
        Optional<Person> personById = bookDao.getPersonById(id);
        if (personById.isPresent()) {
            model.addAttribute("owner", personById.get());
        } else {
            model.addAttribute("people", personDao.getAll());
        }
        model.addAttribute("book", book);
        return "book/book";
    }

    @PostMapping("/new")
    private String addBook(@ModelAttribute("book") Book book) {
        bookDao.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    private String editBook(@ModelAttribute("book") Book book, Model model) {
        model.addAttribute("people", personDao.getAll());
        return "book/edit";
    }

    @PatchMapping("/{id}")
    private String editBook(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookDao.edit(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    private String deleteBook(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    private String releaseBook(@PathVariable("id") int id) {
        bookDao.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    private String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {

        bookDao.assign(id, person.getId());
        return "redirect:/books/" + id;
    }

}
