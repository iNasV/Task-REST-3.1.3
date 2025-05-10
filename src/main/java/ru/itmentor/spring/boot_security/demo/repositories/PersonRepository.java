package ru.itmentor.spring.boot_security.demo.repositories;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {


    Optional<Person> findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByEmail(String string);

    Optional<Person> findByEmail(String email);
}
