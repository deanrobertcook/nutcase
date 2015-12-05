package org.theronin.nutcase.domain.teststep;

import org.theronin.nutcase.domain.base.AuditBaseDTO;
import org.theronin.nutcase.domain.base.MapDept;

public class TestStepDTO extends AuditBaseDTO {

    private String description;

    public TestStepDTO() {
    }

    public TestStepDTO(TestStep entity, MapDept mappingDept) {
        super(entity);
        if (entity != null) {
            mappingDept = MapDept.getEnum(mappingDept.getValue() - 1);

            this.setCreatedBy(entity.getCreatedBy());
            this.setCreatedDate(entity.getCreatedDate());
            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());

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
