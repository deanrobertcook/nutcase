package org.theronin.nutcase.domain.run;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.theronin.nutcase.domain.base.BaseDTO;
import org.theronin.nutcase.domain.execution.Execution;
import org.theronin.nutcase.domain.execution.ExecutionDTO;
import org.theronin.nutcase.domain.testcase.TestCase;
import org.theronin.nutcase.domain.testcase.TestCaseDTO;

public class RunDTO extends BaseDTO {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    private List<TestCaseDTO> testcases = new ArrayList<>();

    private List<ExecutionDTO> executions = new ArrayList<>();

    private String description;

    public RunDTO() {
    }

    public RunDTO(Run entity, int mappingDept) {
        super(entity);
        if (entity != null) {
            mappingDept--;
            this.name = entity.getName();
            this.description = entity.getDescription();
            if (mappingDept > 0) {
                for (TestCase testcase : entity.getTestcases()) {
                    testcases.add(new TestCaseDTO(testcase, mappingDept));
                }
                for (Execution execution : entity.getExecutions()) {
                    executions.add(new ExecutionDTO(execution, mappingDept));
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

    public List<TestCaseDTO> getTestcases() {
        return testcases;
    }

    public void setTestcases(List<TestCaseDTO> testcases) {
        this.testcases = testcases;
    }

    public List<ExecutionDTO> getExecutions() {
        return executions;
    }

    public void setExecutions(List<ExecutionDTO> executions) {
        this.executions = executions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RunDTO{" + "name=" + name + ", testcases=" + testcases + ", executions=" + executions + ", description=" + description + '}';
    }



}
