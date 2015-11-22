package org.theronin.nutcase.domain.project;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.theronin.nutcase.domain.base.BaseEntity;
import org.theronin.nutcase.domain.run.Run;

@Entity
public class Project extends BaseEntity {

    @NotNull
    @Size(min = 1, max = 255)
    @Basic(optional = false)
    @Column(unique = true)
    private String name;

    @OneToMany
    private List<Run> runs;

    private String description;

    protected Project() {
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Run> getRuns() {
        return runs;
    }

    public void setRuns(List<Run> runs) {
        this.runs = runs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
