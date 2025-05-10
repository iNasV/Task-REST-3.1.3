package ru.itmentor.spring.boot_security.demo.annotation.person.login;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ForbiddenPersonLoginsValidator implements ConstraintValidator<ForbiddenPersonLogins, String> {

    private Set<String> forbidden;

    @Override
    public void initialize(ForbiddenPersonLogins constraintAnnotation) {
        forbidden = Arrays.stream(constraintAnnotation.value())
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return login == null || !forbidden.contains(login.toLowerCase());
    }
}
