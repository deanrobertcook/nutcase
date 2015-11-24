package org.theronin.nutcase.domain.teststep;

import org.theronin.nutcase.domain.testcase.TestCase;
import javax.persistence.*;
import org.theronin.nutcase.domain.base.BaseEntity;

@Entity
public class TestStep extends BaseEntity {

    @ManyToOne
    private TestCase testCase;

    private int stepNumber;

    private String description;

    protected TestStep() {
    }

    public TestStep(TestCase testCase, int stepNumber, String description) {
        this.testCase = testCase;
        this.stepNumber = stepNumber;
        this.description = description;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
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

    @Override
    public String toString() {
        return "TestStep{" + "testCase=" + testCase + ", stepNumber=" + stepNumber + ", description=" + description + '}';
    }

}
