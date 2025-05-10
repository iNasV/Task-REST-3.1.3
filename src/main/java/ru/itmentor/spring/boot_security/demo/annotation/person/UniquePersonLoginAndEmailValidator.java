//package ru.itmentor.spring.boot_security.demo.annotation.person;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
////import ru.itmentor.spring.boot_security.demo.configs.CustomSpringContext;
//import ru.itmentor.spring.boot_security.demo.model.Person;
//import ru.itmentor.spring.boot_security.demo.repositories.PersonRepository;
//
//import java.util.Objects;
//
//@Component
//public class UniquePersonLoginAndEmailValidator implements ConstraintValidator<UniquePersonLoginAndEmail, Person> {
//
//
//    private PersonRepository userRepository;
//
//    @Autowired
//    public UniquePersonLoginAndEmailValidator(PersonRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public boolean isValid(Person person, ConstraintValidatorContext constraintValidatorContext) {
//        if (person == null) { return true; }
//
//        // Проверка логина
//        boolean loginIsValid = true;
//        // Получение человека из БД по ____ЛОГИНУ____ человека
//        // Сравнение ID person и ID человека из БД с таким логином
//        System.out.println("Поиск человека с данным логином из БД (логин)");
//        Person dBPersonForLogin = userRepository.findByLogin(person.getLogin()).orElse(null);
//        if (dBPersonForLogin != null) {
//            System.out.println("Человек с таким логином нашелся");
//            if (!Objects.equals(dBPersonForLogin.getId(),person.getId())) {
//                System.out.println("И это два разных пользователя");
//                loginIsValid = false;
//            }
//        }
//
//        // Проверка почты
//        boolean emailIsValid = true;
//        // Получение человека из БД по ____ПОЧТЕ____ человека
//        // Сравнение ID person и ID человека из БД с такой же почтой
//        System.out.println("Поиск человека с данной почтой из БД (почта)");
//        Person dBPerson = userRepository.findByEmail(person.getEmail()).orElse(null);
//        if (dBPerson != null) {
//            System.out.println("Человек с такой почтой нашелся");
//            if (!Objects.equals(dBPerson.getId(),person.getId())) {
//                System.out.println("И это два разных пользователя");
//                emailIsValid = false;
//            }
//        }
//
//        return loginIsValid && emailIsValid;
//    }
//}
