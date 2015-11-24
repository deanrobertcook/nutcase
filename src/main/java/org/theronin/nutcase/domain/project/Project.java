package org.theronin.nutcase.domain.project;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.theronin.nutcase.domain.base.BaseEntity;
import org.theronin.nutcase.domain.run.Run;
import org.theronin.nutcase.domain.testcase.TestCase;

@Entity
public class Project extends BaseEntity {

    @NotNull
    @Size(min = 1, max = 255)
    @Basic(optional = false)
    @Column(unique = true)
    private String name;

    @OneToMany
    private List<Run> runs = new ArrayList();

    @OneToMany
    private List<TestCase> testcases = new ArrayList();

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

    public List<TestCase> getTestcases() {
        return testcases;
    }

    public void setTestcases(List<TestCase> testcases) {
        this.testcases = testcases;
    }

    @Override
    public String toString() {
        return "Project{" + "name=" + name + ", runs=" + runs + ", testcases=" + testcases + ", description=" + description + '}';
    }

}
