package org.theronin.nutcase.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "projects")
    Set<NutcaseUser> users = new HashSet<>();

    @OneToOne
    private NutcaseUser createdBy;

    @Column(unique = true)
    private String name;

    private String description;

    public Project() {
    }

    public Project(NutcaseUser createdBy, String name, String description) {
        this.createdBy = createdBy;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NutcaseUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(NutcaseUser createdBy) {
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
