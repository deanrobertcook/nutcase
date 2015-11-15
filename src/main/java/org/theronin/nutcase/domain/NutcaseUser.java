package org.theronin.nutcase.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
public class NutcaseUser {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(name = "user_project_xref")
    private Set<Project> projects = new HashSet<>();

    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private int privilege;

    protected NutcaseUser() {
    }

    public NutcaseUser(String email, String password, int privilege) {
        this.email = email;
        this.password = password;
        this.privilege = privilege;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }
}
