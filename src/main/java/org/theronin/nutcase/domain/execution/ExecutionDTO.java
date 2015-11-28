package org.theronin.nutcase.domain.execution;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.theronin.nutcase.domain.base.AuditBaseDTO;
import org.theronin.nutcase.domain.execution.testcaseexecution.TestCaseExecution;
import org.theronin.nutcase.domain.execution.testcaseexecution.TestCaseExecutionDTO;

public class ExecutionDTO extends AuditBaseDTO {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    private List<TestCaseExecutionDTO> testCaseExecutions = new ArrayList<>();

    private String description;

    public ExecutionDTO() {
    }

    public ExecutionDTO(Execution entity, int mappingDept) {
        super(entity);
        if (entity != null) {
            mappingDept--;

            this.setCreatedBy(entity.getCreatedBy());
            this.setCreatedDate(entity.getCreatedDate());
            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());

            this.name = entity.getName();
            this.description = entity.getDescription();
            if (mappingDept > 0) {
                for (TestCaseExecution testcase : entity.getTestCaseExecutions()) {
                    testCaseExecutions.add(new TestCaseExecutionDTO(testcase, mappingDept));
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestCaseExecutionDTO> getTestCaseExecutions() {
        return testCaseExecutions;
    }

    public void setTestCaseExecutions(List<TestCaseExecutionDTO> testCaseExecutions) {
        this.testCaseExecutions = testCaseExecutions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
