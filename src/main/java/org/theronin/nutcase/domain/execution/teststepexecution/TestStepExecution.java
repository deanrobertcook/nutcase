package org.theronin.nutcase.domain.execution.teststepexecution;

import javax.persistence.*;
import org.theronin.nutcase.domain.base.BaseEntity;
import org.theronin.nutcase.domain.teststep.TestStep;

@Entity
public class TestStepExecution extends BaseEntity {

    @Basic(optional = true)
    @ManyToOne
    private TestStep testStepRef;

    private String description;

    protected TestStepExecution() {
    }

    public TestStepExecution(TestStepExecutionDTO dto, int mappingDept) {
        super(dto);
        if (dto != null) {
            mappingDept--;
            this.description = dto.getDescription();
        }
    }

    public TestStepExecution(TestStep testStep) {
        this.testStepRef = testStep;
        this.description = testStep.getDescription();
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
