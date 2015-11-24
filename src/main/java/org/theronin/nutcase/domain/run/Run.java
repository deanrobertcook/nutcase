package org.theronin.nutcase.domain.run;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.theronin.nutcase.domain.base.BaseEntity;
import org.theronin.nutcase.domain.execution.Execution;
import org.theronin.nutcase.domain.project.Project;
import org.theronin.nutcase.domain.testcase.TestCase;

@Entity
@Table(indexes = {
    @Index(name = "RUN_NAME_INDEX", columnList = "NAME")
})
public class Run extends BaseEntity {

    @NotNull
    @Size(min = 1, max = 255)
    @Basic(optional = false)
    @Column(unique = true)
    private String name;

    @OneToOne
    private Project project;

    @OneToMany
    private List<TestCase> testcases;

    @OneToMany
    private List<Execution> executions;

    private String description;

    protected Run() {
    }

    public Run(Project project, String name, String description) {
        this.project = project;
        this.name = name;
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestCase> getTestcases() {
        return testcases;
    }

    public void setTestcases(List<TestCase> testcases) {
        this.testcases = testcases;
    }

    public List<Execution> getExecutions() {
        return executions;
    }

    public void setExecutions(List<Execution> executions) {
        this.executions = executions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Run{" + "name=" + name + ", project=" + project + ", testcases=" + testcases + ", executions=" + executions + ", description=" + description + '}';
    }
}
