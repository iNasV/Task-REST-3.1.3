package ru.itmentor.spring.boot_security.demo.util;

import jakarta.validation.ConstraintValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmentor.spring.boot_security.demo.model.Person;
import ru.itmentor.spring.boot_security.demo.repositories.PersonRepository;

import java.util.Objects;

@Component
public class PersonValidator implements Validator {

    private final PersonRepository personRepository;

    public PersonValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (person == null) { return; }

        // Проверка логина
        boolean loginIsValid = true;
        // Получение человека из БД по ____ЛОГИНУ____ человека
        // Сравнение ID person и ID человека из БД с таким логином
        System.out.println("Поиск человека с данным логином из БД (логин)");
        Person dBPersonForLogin = personRepository.findByLogin(person.getLogin()).orElse(null);
        if (dBPersonForLogin != null) {
            System.out.println("Человек с таким логином нашелся");
            if (!Objects.equals(dBPersonForLogin.getId(),person.getId())) {
                System.out.println("И это два разных пользователя");
                loginIsValid = false;
            }
        }

        if (loginIsValid==false) { errors.rejectValue("login","","В поле недопустимый логин");}

        // Проверка почты
        boolean emailIsValid = true;
        // Получение человека из БД по ____ПОЧТЕ____ человека
        // Сравнение ID person и ID человека из БД с такой же почтой
        System.out.println("Поиск человека с данной почтой из БД (почта)");
        Person dBPerson = personRepository.findByEmail(person.getEmail()).orElse(null);
        if (dBPerson != null) {
            System.out.println("Человек с такой почтой нашелся");
            if (!Objects.equals(dBPerson.getId(),person.getId())) {
                System.out.println("И это два разных пользователя");
                emailIsValid = false;
            }
        }

        if (emailIsValid==false) { errors.rejectValue("email","","В поле недопустимая почта");}
    }
}
