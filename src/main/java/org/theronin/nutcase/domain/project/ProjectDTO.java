package org.theronin.nutcase.domain.project;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.theronin.nutcase.domain.base.AuditBaseDTO;
import org.theronin.nutcase.domain.base.MapDept;
import org.theronin.nutcase.domain.run.Run;
import org.theronin.nutcase.domain.run.RunDTO;
import org.theronin.nutcase.domain.testcase.TestCase;
import org.theronin.nutcase.domain.testcase.TestCaseDTO;

public class ProjectDTO extends AuditBaseDTO {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    private List<RunDTO> runs = new ArrayList();

    private List<TestCaseDTO> testcases = new ArrayList();

    private String description;

    public ProjectDTO() {
    }

    public ProjectDTO(Project entity, MapDept mappingDept) {
        super(entity);
        if (entity != null) {
            mappingDept = MapDept.getEnum(mappingDept.getValue() - 1);

            this.setCreatedBy(entity.getCreatedBy());
            this.setCreatedDate(entity.getCreatedDate());
            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());

            this.name = entity.getName();
            this.description = entity.getDescription();
            if (mappingDept.getValue() > 0) {
                for (Run run : entity.getRuns()) {
                    runs.add(new RunDTO(run, mappingDept));
                }
                for (TestCase testcase : entity.getTestcases()) {
                    testcases.add(new TestCaseDTO(testcase, mappingDept));
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

    public List<RunDTO> getRuns() {
        return runs;
    }

    public void setRuns(List<RunDTO> runs) {
        this.runs = runs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TestCaseDTO> getTestcases() {
        return testcases;
    }

    public void setTestcases(List<TestCaseDTO> testcases) {
        this.testcases = testcases;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" + "name=" + name + ", runs=" + runs + ", testcases=" + testcases + ", description=" + description + ", Audit=" + super.toString() + '}';
    }


}
