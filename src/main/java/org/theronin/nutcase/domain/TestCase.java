package org.theronin.nutcase.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
public class TestCase {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Project project;

    @OneToOne
    private NutcaseUser createdBy;

    //TODO figure out how to make this lazy
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "testCase")
    @OrderBy(value = "stepNumber ASC")
    private Set<TestStep> testSteps = new HashSet<>();

    private String description;

    private int weight;

    private boolean automated;

    protected TestCase() {
    }

    public TestCase(Project project, NutcaseUser createdBy, String description) {
        this.project = project;
        this.createdBy = createdBy;
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("TestCase: %s\n", description));
        sb.append(String.format("Number of test Steps: %d\n", testSteps.size()));
        for (TestStep testStep: testSteps) {
            sb.append(String.format("\t%d: %s\n", testStep.getStepNumber(), testStep.getDescription()));
        }
        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public NutcaseUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(NutcaseUser createdBy) {
        this.createdBy = createdBy;
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
}
