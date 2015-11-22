package org.theronin.nutcase.domain.execution.teststepexecution;

import javax.persistence.*;
import org.theronin.nutcase.domain.base.BaseEntity;
import org.theronin.nutcase.domain.execution.testcaseexecution.TestCaseExecution;
import org.theronin.nutcase.domain.teststep.TestStep;

@Entity
public class TestStepExecution extends BaseEntity {

    @Basic(optional = true)
    @ManyToOne
    private TestStep testStepRef;

    @ManyToOne
    private TestCaseExecution testCaseExecution;

    private int stepNumber;

    private String description;

    protected TestStepExecution() {
    }

    public TestStepExecution(TestStep testStep) {
        this.testStepRef = testStep;
        this.stepNumber = testStep.getStepNumber();
        this.description = testStep.getDescription();
    }

    public TestCaseExecution getTestCaseExecution() {
        return testCaseExecution;
    }

    public void setTestCaseExecution(TestCaseExecution testCaseExecution) {
        this.testCaseExecution = testCaseExecution;
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

    public TestStep getTestStepRef() {
        return testStepRef;
    }

    public void setTestStepRef(TestStep testStepRef) {
        this.testStepRef = testStepRef;
    }

}
