
package com.example.crud.model;


import org.springframework.security.core.GrantedAuthority;

import javax.annotation.security.RolesAllowed;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    public Long getId() {
        return id;
    }

    public Role() {

    }

    public Role(String role) {
        this.role = role;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return role.equalsIgnoreCase(role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}