package ru.demakov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.demakov.library.dao.PersonDao;
import ru.demakov.library.model.Person;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonDao personDao;

    @Autowired
    public PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    public String getPeople(Model model) {
        model.addAttribute("people", personDao.getAll());
        return "person/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable int id, Model model) {
        Person person = personDao.getPerson(id);
        model.addAttribute("person", person);
        return "person/person";
    }

    @GetMapping("/new")
    public String addPerson(Model model) {
        Person person = new Person();
        model.addAttribute("person", person);
        return "person/new";
    }

    @PostMapping("/new")
    private String addPerson(@ModelAttribute("person") Person person) {
        personDao.create(person);
        return "redirect:/people";
    }

    @PostMapping("/{id}")
    private String editPerson(@PathVariable("id") int id, Model model) {
        Person person = personDao.getPerson(id);
        model.addAttribute("person", person);
        return "person/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editPerson(@ModelAttribute("person") Person person) {
        personDao.edit(person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }
}
