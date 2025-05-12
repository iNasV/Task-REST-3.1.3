package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Person;
import ru.itmentor.spring.boot_security.demo.repositories.PersonRepository;
import ru.itmentor.spring.boot_security.demo.util.PersonNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;


    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly=true)
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Person findOne(long id) {
        var optional = personRepository.findById(id);
        return optional.orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(long id, Person updatedPerson) {
        personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(long id) {
        personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        personRepository.deleteById(id);
    }
}
