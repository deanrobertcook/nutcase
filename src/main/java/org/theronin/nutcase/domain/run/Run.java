package org.theronin.nutcase.domain.run;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.theronin.nutcase.domain.audit.AbstractAuditingEntity;
import org.theronin.nutcase.domain.execution.Execution;
import org.theronin.nutcase.domain.execution.ExecutionDTO;
import org.theronin.nutcase.domain.testcase.TestCase;
import org.theronin.nutcase.domain.testcase.TestCaseDTO;

@Entity
@Table(indexes = {
    @Index(name = "RUN_NAME_INDEX", columnList = "NAME")
})
public class Run extends AbstractAuditingEntity {

    @NotNull
    @Size(min = 1, max = 255)
    @Basic(optional = false)
    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TestCase> testcases = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Execution> executions = new ArrayList<>();

    private String description;

    protected Run() {
    }

    public Run(RunDTO dto, int mappingDept) {
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
                for (TestCaseDTO testcase : dto.getTestcases()) {
                    testcases.add(new TestCase(testcase, mappingDept));
                }
                for (ExecutionDTO execution : dto.getExecutions()) {
                    executions.add(new Execution(execution, mappingDept));
                }
            }
        }
    }

    public Run(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestCase> getTestcases() {
        return testcases;
    }

    public void setTestcases(List<TestCase> testcases) {
        this.testcases = testcases;
    }

    public List<Execution> getExecutions() {
        return executions;
    }

    public void setExecutions(List<Execution> executions) {
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
        return "Run{" + "name=" + name + ", testcases=" + testcases + ", executions=" + executions + ", description=" + description + '}';
    }


}
