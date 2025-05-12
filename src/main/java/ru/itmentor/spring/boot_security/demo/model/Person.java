package ru.itmentor.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
//import ru.itmentor.spring.boot_security.demo.annotation.person.UniquePersonLoginAndEmail;
import ru.itmentor.spring.boot_security.demo.annotation.person.login.ForbiddenPersonLogins;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.itmentor.spring.boot_security.demo.model.Role.ROLE_COMPARATOR;

@Entity
@Table(name = "persons")
//@UniquePersonLoginAndEmail
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 32, message = "Имя должно быть от 2 до 32 символов длиной")
    @Pattern(regexp = "^[А-Яа-яЁё]+$", message = "Имя должно содержать символы кириллицы")
    @Column(name = "name")
    private String name;

    @Pattern(regexp = "^[A-Za-z\\d]+$", message = "Логин может содержать только латиницу и цифры")
    @Size(min = 2, max = 32, message = "Логин должен содержать от 2 до 32 символов")
    @ForbiddenPersonLogins({"user","admin"})
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password")
    private String password;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="person_roles",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public Person() {}
    public Person(String name, String login, String password, String email) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Role> getRoles() {
        return roles.stream()
                .sorted(ROLE_COMPARATOR)
                .collect(Collectors.toList());
    }
    public void setRoles(List<Role> roles) { this.roles = roles; }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", login=" + login + ", password=" + password+ ", email=" + email + ", role=" + roles + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(login, person.login) && Objects.equals(password, person.password) && Objects.equals(email, person.email) && Objects.equals(roles, person.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, password, email);
    }
}
