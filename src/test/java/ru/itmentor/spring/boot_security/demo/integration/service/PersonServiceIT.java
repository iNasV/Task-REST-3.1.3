package ru.itmentor.spring.boot_security.demo.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itmentor.spring.boot_security.demo.integration.annotation.IT;
import ru.itmentor.spring.boot_security.demo.model.Person;
import ru.itmentor.spring.boot_security.demo.repositories.PersonRepository;
import ru.itmentor.spring.boot_security.demo.service.PersonService;
import ru.itmentor.spring.boot_security.demo.service.RoleService;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonServiceIT {

    /*
    Сейчас автоинкремент ожидает ID 22
     */

    private static Long idTest = 17L;
    private static Long id = 27L;

    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Test
    void testPasswordEncoder() {
        String password = passwordEncoder.encode("testController");
        System.out.println(password);
    }

    @Test
    @Order(1)
    void findAll() {
        var actualResult = personService.findAll();
        assertFalse(actualResult.toString().isEmpty());
    }

    @Test
    @Order(2)
    void findOne() {
        var actualResult = personService.findOne(idTest);
        assertFalse(actualResult.toString().isEmpty());

        var expectedResult = new Person();
        expectedResult.setId(idTest);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @Order(3)
    void save() {
        var expectedResult = new Person();
        expectedResult.setName("ТестСохр");
        expectedResult.setLogin("testSave");
        expectedResult.setPassword(passwordEncoder.encode("testSave"));
        expectedResult.setEmail("testSave@test.com");
        expectedResult.setRoles(roleService.findAll());

        personService.save(expectedResult);

        var actualResult = personService.findOne(id);

        assertEquals(expectedResult, actualResult);
    }



    @Test
    @Order(4)
    void update() {
        var updatePerson = personService.findOne(id);

        updatePerson.setName("НовоеИмя");

        personService.update(id,updatePerson);

        var actualResult = personService.findOne(id);

        assertEquals(updatePerson.getName(), actualResult.getName());
    }

    @Test
    @Order(5)
    void delete() {
        assertDoesNotThrow(() -> personService.delete(id));
    }
}