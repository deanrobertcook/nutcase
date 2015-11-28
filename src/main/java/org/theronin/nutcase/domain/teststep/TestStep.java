package org.theronin.nutcase.domain.teststep;

import javax.persistence.*;
import org.theronin.nutcase.domain.base.BaseEntity;

@Entity
public class TestStep extends BaseEntity {

    private String description;

    protected TestStep() {
    }

    public TestStep(TestStepDTO dto, int mappingDept) {
        super(dto);
        if (dto != null) {
            mappingDept--;
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
