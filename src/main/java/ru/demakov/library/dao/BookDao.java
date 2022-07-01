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
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> getBook(String name) {
        return jdbcTemplate.query("SELECT FROM Book WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }
    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name,author,year) VALUES(?,?,?)",
                book.getName(), book.getAuthor(),book.getYear());
    }

    public void edit(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET name=?,author=?,year=? WHERE id=?",
                book.getName(), book.getAuthor(),book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public void assign(int bookId, int personId) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", personId, bookId);
    }

    public void release(int bookId) {
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE id=?", bookId);
    }

    public Optional<Person> getPersonById(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny();
    }


}
