package ru.itmentor.spring.boot_security.demo.integration.annotation;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.itmentor.spring.boot_security.demo.integration.TestSpringBootSecurityDemoApplicationTests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@SpringBootTest(classes = TestSpringBootSecurityDemoApplicationTests.class)
public @interface IT {


}
