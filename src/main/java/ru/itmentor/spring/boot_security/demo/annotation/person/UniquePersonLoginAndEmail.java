//package ru.itmentor.spring.boot_security.demo.annotation.person;
//
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//
//@Target({ElementType.TYPE})
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = UniquePersonLoginAndEmailValidator.class)
//public @interface UniquePersonLoginAndEmail {
//    String message() default "Логин или почта уже используется";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}
