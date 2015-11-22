package org.theronin.nutcase.domain.testcase;

import org.theronin.nutcase.domain.run.Run;
import javax.persistence.*;
import java.util.List;
import org.theronin.nutcase.domain.base.BaseEntity;
import org.theronin.nutcase.domain.teststep.TestStep;

@Entity
@Table(indexes = {
    @Index(name = "TEST_ID_INDEX", columnList = "TESTID", unique = true)
})
public class TestCase extends BaseEntity {

    @ManyToOne
    private Run run;

    @Column(unique = true)
    private String testId;

    @OneToMany
    private List<TestStep> teststeps;

    private String description;

    private int weight;

    private boolean automated;

    protected TestCase() {
    }

    public TestCase(Run run, String description) {
        this.run = run;
        this.description = description;
    }

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    public List<TestStep> getTeststeps() {
        return teststeps;
    }

    public void setTeststeps(List<TestStep> teststeps) {
        this.teststeps = teststeps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isAutomated() {
        return automated;
    }

    public void setAutomated(boolean automated) {
        this.automated = automated;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

}
