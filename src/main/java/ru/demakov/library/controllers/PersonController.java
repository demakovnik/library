package ru.demakov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.demakov.library.dao.PersonDao;
import ru.demakov.library.model.Book;
import ru.demakov.library.model.Person;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonDao personDao;

    @Autowired
    public PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    private String getPeople(Model model) {
        model.addAttribute("people", personDao.getAll());
        return "person/people";
    }

    @GetMapping("/{id}")
    private String showPerson(@PathVariable int id, Model model) {
        Person person = personDao.getPerson(id);
        List<Book> booksByPersonId = personDao.getBooksByPersonId(id);
        model.addAttribute("books", booksByPersonId);
        model.addAttribute("person", person);
        return "person/person";
    }

    @GetMapping("/new")
    private String addPerson(Model model) {
        Person person = new Person();
        model.addAttribute("person", person);
        return "person/new";
    }

    @PostMapping("/new")
    private String addPerson(@ModelAttribute("person") Person person) {
        personDao.create(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    private String editPerson(@ModelAttribute("person") Person person) {
        return "person/edit";
    }

    @PatchMapping("/{id}")
    private String editPerson(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personDao.edit(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    private String deletePerson(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }
}
