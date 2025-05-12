package ru.itmentor.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<Person> ownersList;

    @Column(name = "role")
    private String role;

    public static final Comparator<Role> ROLE_COMPARATOR = Comparator.comparing(
            r -> {
                if ("ROLE_ADMIN".equals(r.getRole())) return 0;
                if ("ROLE_USER".equals(r.getRole())) return 1;
                return 2;
            }
    );

    public Role() {}

    public Role(int id, String role) {
        this.id = id;
        this.role = role;
    }
    public int getId() { return id; }
    public String getRole() { return role; }
    public void setRole(String role) {this.role = role;}
    public List<Person> getOwnersList() { return ownersList; }
    public void setOwnersList(List<Person> ownersList) { this.ownersList = ownersList; }

    @Override
    public String toString() {
        return role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return id == role1.id && Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
