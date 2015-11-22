package org.theronin.nutcase.domain.execution;

import org.theronin.nutcase.domain.run.Run;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.theronin.nutcase.domain.base.BaseEntity;
import org.theronin.nutcase.domain.testcase.TestCase;

@Entity
@Table(indexes = {
    @Index(name = "EXECUTION_NAME_INDEX", columnList = "NAME")
})
public class Execution extends BaseEntity {

    @OneToOne
    private Run run;

    @NotNull
    @Size(min = 1, max = 255)
    @Basic(optional = false)
    @Column(unique = true)
    private String name;

    @OneToMany
    private List<TestCase> testCaseExecutions;

    private String description;

    public Execution() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    public List<TestCase> getTestCaseExecutions() {
        return testCaseExecutions;
    }

    public void setTestCaseExecutions(List<TestCase> testCaseExecutions) {
        this.testCaseExecutions = testCaseExecutions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
