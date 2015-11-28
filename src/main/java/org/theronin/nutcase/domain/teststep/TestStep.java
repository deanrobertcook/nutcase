package org.theronin.nutcase.domain.teststep;

import javax.persistence.*;
import org.theronin.nutcase.domain.audit.AbstractAuditingEntity;

@Entity
public class TestStep extends AbstractAuditingEntity {

    private String description;

    protected TestStep() {
    }

    public TestStep(TestStepDTO dto, int mappingDept) {
        super(dto);
        if (dto != null) {
            mappingDept--;

            this.setCreatedBy(dto.getCreatedBy());
            this.setCreatedDate(dto.getCreatedDate());
            this.setLastModifiedBy(dto.getLastModifiedBy());
            this.setLastModifiedDate(dto.getLastModifiedDate());

            this.description = dto.getDescription();
        }
    }

    public TestStep(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TestStep{" + "description=" + description + '}';
    }

}
