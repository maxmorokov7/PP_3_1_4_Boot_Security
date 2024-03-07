package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "role")
    private String role;

    public Role() { }

    @Override
    public String toString() {
        return role;
    }


    @Override
    public String getAuthority() {
        return role;
    }

    public String getRole() {
        return role;
    }
}