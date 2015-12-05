package org.theronin.nutcase.domain.testcase;

import java.util.ArrayList;
import javax.persistence.*;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.theronin.nutcase.domain.audit.AbstractAuditingEntity;
import org.theronin.nutcase.domain.base.MapDept;
import org.theronin.nutcase.domain.teststep.TestStep;
import org.theronin.nutcase.domain.teststep.TestStepDTO;

@Entity
@Table(indexes = {
    @Index(name = "TEST_ID_INDEX", columnList = "TESTID", unique = true)
})
public class TestCase extends AbstractAuditingEntity {

    @NotNull
    @Column(unique = true)
    private Long testId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TestStep> teststeps = new ArrayList<>();

    private String description;

    private int weight;

    private boolean automated;

    protected TestCase() {
    }

    public TestCase(TestCaseDTO dto, MapDept mappingDept) {
        super(dto);
        if (dto != null) {
            mappingDept = MapDept.getEnum(mappingDept.getValue() - 1);

            this.setCreatedBy(dto.getCreatedBy());
            this.setCreatedDate(dto.getCreatedDate());
            this.setLastModifiedBy(dto.getLastModifiedBy());
            this.setLastModifiedDate(dto.getLastModifiedDate());

            this.testId = dto.getTestId();
            this.description = dto.getDescription();
            this.weight = dto.getWeight();
            this.automated = dto.isAutomated();
            if (mappingDept.getValue() > 0) {
                for (TestStepDTO teststep : dto.getTeststeps()) {
                    teststeps.add(new TestStep(teststep, mappingDept));
                }
            }
        }
    }

    public TestCase(Long testId, String description) {
        this.testId = testId;
        this.description = description;
    }

    public List<TestStep> getTeststeps() {
        return teststeps;
    }

    public void setTeststeps(List<TestStep> teststeps) {
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
        return "TestCase{" + "testId=" + testId + ", teststeps=" + teststeps + ", description=" + description + ", weight=" + weight + ", automated=" + automated + '}';
    }


}
