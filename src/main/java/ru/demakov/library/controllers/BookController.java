package ru.demakov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.demakov.library.dao.BookDao;
import ru.demakov.library.model.Book;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;

    @Autowired
    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
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
    private String showBook(@PathVariable("id") int id, Model model) {
        Book book = bookDao.getBook(id);
        model.addAttribute("book", book);
        return "book/book";
    }

    @PostMapping("/new")
    private String addBook(@ModelAttribute("book") Book book) {
        bookDao.create(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    private String editBook(@PathVariable("id") int id, Model model) {
        Book book = bookDao.getBook(id);
        model.addAttribute("book", book);
        return "book/edit";
    }

    @PatchMapping("/{id}/edit")
    private String editBook(@ModelAttribute("book") Book book) {
        bookDao.edit(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    private String deleteBook(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }
}
