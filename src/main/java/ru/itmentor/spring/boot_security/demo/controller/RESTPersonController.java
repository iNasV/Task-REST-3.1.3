package ru.itmentor.spring.boot_security.demo.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Person;
import ru.itmentor.spring.boot_security.demo.service.PersonService;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.util.PersonErrorResponse;
import ru.itmentor.spring.boot_security.demo.util.PersonNotCreatedException;
import ru.itmentor.spring.boot_security.demo.util.PersonNotFoundException;
import ru.itmentor.spring.boot_security.demo.util.PersonValidator;

import java.util.List;

@RestController
public class RESTPersonController {

    private final PersonService personService;
    private final PersonValidator personValidator;
    private final PasswordEncoder passwordEncoder;

    public RESTPersonController(PersonService personService, PersonValidator personValidator, PasswordEncoder passwordEncoder) {
        this.personService = personService;
        this.personValidator = personValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/API/showAllPersons")
    public List<Person> showAllPersons() {
        return personService.findAll();
    }

    @GetMapping("/API/showOnePersonByID")
    public Person showOnePersonByID(@RequestParam("id") Long id) {
        return personService.findOne(id);
    }

    @PostMapping("/API/createNewPerson")
    public ResponseEntity<HttpStatus> createNewPerson(@RequestBody @Valid Person person, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorsMsg = new StringBuilder();
            var fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorsMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorsMsg.toString());
        }

        var string = passwordEncoder.encode(person.getPassword());
        person.setPassword(string);

        personService.save(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/API/updatePersonByID")
    public ResponseEntity<HttpStatus> updatePersonByID(@RequestBody @Valid Person person, @RequestParam("id") long id, BindingResult bindingResult) {
        personService.findOne(id);
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorsMsg = new StringBuilder();
            var fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorsMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorsMsg.toString());
        }

        var string = passwordEncoder.encode(person.getPassword());
        person.setPassword(string);

        personService.update(id, person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/API/deletePersonByID")
    public ResponseEntity<HttpStatus> deletePersonByID(@RequestParam("id") long id) {
        personService.findOne(id);
        personService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Пользователь с таким ID ("+e.getId()+") не был найден.",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
