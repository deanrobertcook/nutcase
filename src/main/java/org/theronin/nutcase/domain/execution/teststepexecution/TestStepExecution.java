package org.theronin.nutcase.domain.execution.teststepexecution;

import javax.persistence.*;
import org.theronin.nutcase.domain.audit.AbstractAuditingEntity;
import org.theronin.nutcase.domain.base.MapDept;
import org.theronin.nutcase.domain.teststep.TestStep;

@Entity
public class TestStepExecution extends AbstractAuditingEntity {

    @Basic(optional = true)
    @ManyToOne
    private TestStep testStepRef;

    private String description;

    protected TestStepExecution() {
    }

    public TestStepExecution(TestStepExecutionDTO dto, MapDept mappingDept) {
        super(dto);
        if (dto != null) {
            mappingDept = MapDept.getEnum(mappingDept.getValue() - 1);

            this.setCreatedBy(dto.getCreatedBy());
            this.setCreatedDate(dto.getCreatedDate());
            this.setLastModifiedBy(dto.getLastModifiedBy());
            this.setLastModifiedDate(dto.getLastModifiedDate());

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
