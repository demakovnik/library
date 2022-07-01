package ru.demakov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.demakov.library.model.Book;
import ru.demakov.library.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM Person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> getPerson(String name) {
        return jdbcTemplate.query("SELECT FROM Person WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public Person getPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public void create(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name,year) VALUES(?,?)",
                person.getName(), person.getYear());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public void edit(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?,year=? WHERE id=?",
                person.getName(), person.getYear(), id);
    }

    public List<Book> getBooksByPersonId(int personId) {
        return jdbcTemplate.query("SELECT Book.* FROM Person JOIN Book ON Person.id = Book.person_id WHERE Person.id = ?",
                new Object[]{personId},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
