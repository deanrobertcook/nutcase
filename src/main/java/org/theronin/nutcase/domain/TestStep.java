package org.theronin.nutcase.domain;

import javax.persistence.*;

/**
 *
 */
@Entity
public class TestStep {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private TestCase testCase;

    @OneToOne
    private NutcaseUser createdBy;

    private int stepNumber;

    private String description;

    protected TestStep() {
    }

    public TestStep(TestCase testCase, NutcaseUser createdBy, int stepNumber, String description) {
        this.testCase = testCase;
        this.createdBy = createdBy;
        this.stepNumber = stepNumber;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public NutcaseUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(NutcaseUser createdBy) {
        this.createdBy = createdBy;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
