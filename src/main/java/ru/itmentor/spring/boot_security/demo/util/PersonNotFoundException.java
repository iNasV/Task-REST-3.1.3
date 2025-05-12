package ru.itmentor.spring.boot_security.demo.util;

public class PersonNotFoundException extends RuntimeException {

    private long id;

    public PersonNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
