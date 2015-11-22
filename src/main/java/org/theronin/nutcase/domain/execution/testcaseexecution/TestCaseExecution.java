package org.theronin.nutcase.domain.execution.testcaseexecution;

import java.util.ArrayList;
import javax.persistence.*;
import java.util.List;
import org.theronin.nutcase.domain.base.BaseEntity;
import org.theronin.nutcase.domain.execution.Execution;
import org.theronin.nutcase.domain.execution.teststepexecution.TestStepExecution;
import org.theronin.nutcase.domain.testcase.TestCase;
import org.theronin.nutcase.domain.teststep.TestStep;

@Entity
public class TestCaseExecution extends BaseEntity {

    @Basic(optional = true)
    @ManyToOne
    private TestCase testCaseRef;

    @ManyToOne
    private Execution execution;

    @OneToMany
    private List<TestStepExecution> testStepExecutions;

    private String description;

    private int weight;

    private boolean automated;

    protected TestCaseExecution() {
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

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
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
