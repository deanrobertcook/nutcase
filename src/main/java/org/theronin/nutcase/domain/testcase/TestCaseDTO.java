package org.theronin.nutcase.domain.testcase;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.theronin.nutcase.domain.base.AuditBaseDTO;
import org.theronin.nutcase.domain.base.MapDept;
import org.theronin.nutcase.domain.teststep.TestStep;
import org.theronin.nutcase.domain.teststep.TestStepDTO;

public class TestCaseDTO extends AuditBaseDTO {

    @NotNull
    private Long testId;

    private List<TestStepDTO> teststeps = new ArrayList<>();

    private String description;

    private int weight;

    private boolean automated;

    public TestCaseDTO() {
    }

    public TestCaseDTO(TestCase entity, MapDept mappingDept) {
        super(entity);
        if (entity != null) {
            mappingDept = MapDept.getEnum(mappingDept.getValue() - 1);

            this.setCreatedBy(entity.getCreatedBy());
            this.setCreatedDate(entity.getCreatedDate());
            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());

            this.testId = entity.getTestId();
            this.description = entity.getDescription();
            this.weight = entity.getWeight();
            this.automated = entity.isAutomated();
            if (mappingDept.getValue() > 0) {
                for (TestStep teststep : entity.getTeststeps()) {
                    teststeps.add(new TestStepDTO(teststep, mappingDept));
                }
            }
        }
    }

    public List<TestStepDTO> getTeststeps() {
        return teststeps;
    }

    public void setTeststeps(List<TestStepDTO> teststeps) {
        this.teststeps = teststeps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isAutomated() {
        return automated;
    }

    public void setAutomated(boolean automated) {
        this.automated = automated;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    @Override
    public String toString() {
        return "TestCaseDTO{" + "testId=" + testId + ", teststeps=" + teststeps + ", description=" + description + ", weight=" + weight + ", automated=" + automated + '}';
    }



}
