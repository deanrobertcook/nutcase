package org.theronin.nutcase.domain.execution.teststepexecution;

import org.theronin.nutcase.domain.base.BaseDTO;
import org.theronin.nutcase.domain.teststep.TestStepDTO;

public class TestStepExecutionDTO extends BaseDTO {

    private TestStepDTO testStepRef;

    private int stepNumber;

    private String description;

    public TestStepExecutionDTO() {
    }


    public TestStepExecutionDTO(TestStepExecution entity, int mappingDept) {
        super(entity);
        if (entity != null) {
            mappingDept--;
            this.description = entity.getDescription();
        }
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

    public TestStepDTO getTestStepRef() {
        return testStepRef;
    }

    public void setTestStepRef(TestStepDTO testStepRef) {
        this.testStepRef = testStepRef;
    }

}
