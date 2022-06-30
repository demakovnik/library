package ru.demakov.library.model;

import javax.validation.constraints.NotEmpty;

public class Person {

    private int id;

    @NotEmpty(message = "Name should not be Empty")
    private String name;

    private int year;

    public Person() {
    }

    public Person(int id, String name, String surname, int year) {
        this.id = id;
        this.name = name;
        this.year = year;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
