package org.theronin.nutcase.domain.project;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.theronin.nutcase.domain.audit.AbstractAuditingEntity;
import org.theronin.nutcase.domain.run.Run;
import org.theronin.nutcase.domain.run.RunDTO;
import org.theronin.nutcase.domain.testcase.TestCase;
import org.theronin.nutcase.domain.testcase.TestCaseDTO;

@Entity
public class Project extends AbstractAuditingEntity {

    @NotNull
    @Size(min = 1, max = 255)
    @Basic(optional = false)
    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Run> runs = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    private List<TestCase> testcases = new ArrayList();

    private String description;

    protected Project() {
    }

    public Project(ProjectDTO dto, int mappingDept) {
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
                for (RunDTO run : dto.getRuns()) {
                    runs.add(new Run(run, mappingDept));
                }
                for (TestCaseDTO testcase : dto.getTestcases()) {
                    testcases.add(new TestCase(testcase, mappingDept));
                }
            }
        }
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Run> getRuns() {
        return runs;
    }

    public void setRuns(List<Run> runs) {
        this.runs = runs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TestCase> getTestcases() {
        return testcases;
    }

    public void setTestcases(List<TestCase> testcases) {
        this.testcases = testcases;
    }

    @Override
    public String toString() {
        return "Project{" + "name=" + name + ", runs=" + runs + ", testcases=" + testcases + ", description=" + description + ", Audit=" + super.toString() + '}';
    }

}
