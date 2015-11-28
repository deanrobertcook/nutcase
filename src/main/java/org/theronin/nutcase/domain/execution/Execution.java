package org.theronin.nutcase.domain.execution;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.theronin.nutcase.domain.audit.AbstractAuditingEntity;
import org.theronin.nutcase.domain.execution.testcaseexecution.TestCaseExecution;
import org.theronin.nutcase.domain.execution.testcaseexecution.TestCaseExecutionDTO;

@Entity
@Table(indexes = {
    @Index(name = "EXECUTION_NAME_INDEX", columnList = "NAME")
})
public class Execution extends AbstractAuditingEntity {

    @NotNull
    @Size(min = 1, max = 255)
    @Basic(optional = false)
    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TestCaseExecution> testCaseExecutions = new ArrayList<>();

    private String description;

    protected Execution() {
    }

    public Execution(ExecutionDTO dto, int mappingDept) {
        super(dto);
        if (dto != null) {
            mappingDept--;

            this.setCreatedBy(dto.getCreatedBy());
            this.setCreatedDate(dto.getCreatedDate());
            this.setLastModifiedBy(dto.getLastModifiedBy());
            this.setLastModifiedDate(dto.getLastModifiedDate());

            this.name = dto.getName();
            this.description = dto.getDescription();
            if (mappingDept > 0) {
                for (TestCaseExecutionDTO testCaseExecution : dto.getTestCaseExecutions()) {
                    testCaseExecutions.add(new TestCaseExecution(testCaseExecution, mappingDept));
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

    public List<TestCaseExecution> getTestCaseExecutions() {
        return testCaseExecutions;
    }

    public void setTestCaseExecutions(List<TestCaseExecution> testCaseExecutions) {
        this.testCaseExecutions = testCaseExecutions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
