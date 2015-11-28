package org.theronin.nutcase.domain.execution.testcaseexecution;

import java.util.ArrayList;
import java.util.List;
import org.theronin.nutcase.domain.base.AuditBaseDTO;
import org.theronin.nutcase.domain.execution.teststepexecution.TestStepExecution;
import org.theronin.nutcase.domain.execution.teststepexecution.TestStepExecutionDTO;
import org.theronin.nutcase.domain.testcase.TestCaseDTO;

public class TestCaseExecutionDTO extends AuditBaseDTO {

    private TestCaseDTO testCaseRef;

    private List<TestStepExecutionDTO> testStepExecutions = new ArrayList<>();

    private String description;

    private int weight;

    private boolean automated;

    public TestCaseExecutionDTO() {
    }

    public TestCaseExecutionDTO(TestCaseExecution entity, int mappingDept) {
        super(entity);
        if (entity != null) {
            mappingDept--;

            this.setCreatedBy(entity.getCreatedBy());
            this.setCreatedDate(entity.getCreatedDate());
            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());

            this.description = entity.getDescription();
            this.weight = entity.getWeight();
            this.automated = entity.isAutomated();
            if (mappingDept > 0) {
                for (TestStepExecution testStepExecution : entity.getTestStepExecutions()) {
                    testStepExecutions.add(new TestStepExecutionDTO(testStepExecution, mappingDept));
                }
            }
        }
    }

    public List<TestStepExecutionDTO> getTestStepExecutions() {
        return testStepExecutions;
    }

    public void setTestStepExecutions(List<TestStepExecutionDTO> testStepExecutions) {
        this.testStepExecutions = testStepExecutions;
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

    public TestCaseDTO getTestCaseRef() {
        return testCaseRef;
    }

    public void setTestCaseRef(TestCaseDTO testCaseRef) {
        this.testCaseRef = testCaseRef;
    }

}
