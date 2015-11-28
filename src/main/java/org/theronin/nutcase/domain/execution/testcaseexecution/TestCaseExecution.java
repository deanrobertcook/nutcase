package org.theronin.nutcase.domain.execution.testcaseexecution;

import java.util.ArrayList;
import javax.persistence.*;
import java.util.List;
import org.theronin.nutcase.domain.base.BaseEntity;
import org.theronin.nutcase.domain.execution.teststepexecution.TestStepExecution;
import org.theronin.nutcase.domain.execution.teststepexecution.TestStepExecutionDTO;
import org.theronin.nutcase.domain.testcase.TestCase;
import org.theronin.nutcase.domain.teststep.TestStep;

@Entity
public class TestCaseExecution extends BaseEntity {

    @Basic(optional = true)
    @ManyToOne
    private TestCase testCaseRef;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TestStepExecution> testStepExecutions = new ArrayList<>();

    private String description;

    private int weight;

    private boolean automated;

    protected TestCaseExecution() {
    }

    public TestCaseExecution(TestCaseExecutionDTO dto, int mappingDept) {
        super(dto);
        if (dto != null) {
            mappingDept--;
            this.description = dto.getDescription();
            this.weight = dto.getWeight();
            this.automated = dto.isAutomated();
            if (mappingDept > 0) {
                for (TestStepExecutionDTO testStepExecution : dto.getTestStepExecutions()) {
                    testStepExecutions.add(new TestStepExecution(testStepExecution, mappingDept));
                }
            }
        }
    }

    public TestCaseExecution(TestCase testCase) {
        this.testCaseRef = testCase;
        testStepExecutions = new ArrayList();
        for (TestStep teststep : testCase.getTeststeps()) {
            testStepExecutions.add(new TestStepExecution(teststep));
        }
        this.description = testCase.getDescription();
        this.weight = testCase.getWeight();
        this.automated = testCase.isAutomated();
    }

    public List<TestStepExecution> getTestStepExecutions() {
        return testStepExecutions;
    }

    public void setTestStepExecutions(List<TestStepExecution> testStepExecutions) {
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

    public TestCase getTestCaseRef() {
        return testCaseRef;
    }

    public void setTestCaseRef(TestCase testCaseRef) {
        this.testCaseRef = testCaseRef;
    }

}
