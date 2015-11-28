package org.theronin.nutcase.domain.teststep;

import org.theronin.nutcase.domain.base.BaseDTO;

public class TestStepDTO extends BaseDTO {

    private String description;

    public TestStepDTO() {
    }

    public TestStepDTO(TestStep entity, int mappingDept) {
        super(entity);
        if (entity != null) {
            mappingDept--;
            this.description = entity.getDescription();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TestStepDTO{" + "description=" + description + '}';
    }
}
