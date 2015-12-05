package org.theronin.nutcase.domain.execution.testcaseexecution;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.theronin.nutcase.Application;
import org.theronin.nutcase.domain.execution.ExecutionRepository;
import org.theronin.nutcase.domain.execution.teststepexecution.TestStepExecutionDTO;
import org.theronin.nutcase.domain.project.ProjectRepository;
import org.theronin.nutcase.domain.testcase.TestCaseRepository;
import org.theronin.nutcase.domain.teststep.TestStepRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestCaseExecutionServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TestCaseExecutionServiceTest.class);

    @Rule
    public TestName name = new TestName();

    @Inject
    private TestCaseExecutionService testCaseExecutionService;

    @Inject
    private ExecutionRepository executionRepository;

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private TestCaseRepository testCaseRepository;

    @Inject
    private TestStepRepository testStepRepository;

    @Before
    public void initTest() {
        projectRepository.deleteAllInBatch();
        executionRepository.deleteAllInBatch();
        testCaseRepository.deleteAllInBatch();
        testStepRepository.deleteAllInBatch();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateNullTestCaseExecution() {

        log.info(name.getMethodName());
        testCaseExecutionService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateTestCaseExecutionWithID() {
        log.info(name.getMethodName());
        TestCaseExecutionDTO testcaseexecution = new TestCaseExecutionDTO();
        testcaseexecution.setId(Long.MAX_VALUE);
        testCaseExecutionService.create(testcaseexecution);
    }

    @Ignore//nothing to validate yet
    @Test(expected = ConstraintViolationException.class)
    public void shouldNotCreateInvalidTestCaseExecution() {
        log.info(name.getMethodName());
        TestCaseExecutionDTO testcaseexecution = new TestCaseExecutionDTO();
        testCaseExecutionService.create(testcaseexecution);
    }

    @Test
    public void shouldCreateTestCaseExecution() {
        log.info(name.getMethodName());
        TestCaseExecutionDTO testcaseexecution = new TestCaseExecutionDTO();
        testcaseexecution.setDescription("testcasetestcaseexecutionDesc1");
        testcaseexecution = testCaseExecutionService.create(testcaseexecution);
        TestCaseExecutionDTO savedTestCaseExecution = testCaseExecutionService.read(testcaseexecution.getId());
        Assert.assertEquals("Returned testcaseexecution should have the new ID", testcaseexecution.getId(), savedTestCaseExecution.getId());
    }

    @Test
    public void shouldDeleteTestCaseExecution() {
        log.info(name.getMethodName());
        TestCaseExecutionDTO testcaseexecution = new TestCaseExecutionDTO();
        testcaseexecution.setDescription("testcasetestcaseexecutionDesc1");
        testcaseexecution = testCaseExecutionService.create(testcaseexecution);
        TestCaseExecutionDTO savedTestCaseExecution = testCaseExecutionService.read(testcaseexecution.getId());
        Assert.assertEquals("Returned testcaseexecution should have the new ID", testcaseexecution.getId(), savedTestCaseExecution.getId());

        testCaseExecutionService.delete(testcaseexecution);
        TestCaseExecutionDTO deletedTestCaseExecution = testCaseExecutionService.read(testcaseexecution.getId());
        Assert.assertTrue("TestCaseExecution should not be found anymore", deletedTestCaseExecution == null);
    }

    @Test
    public void shouldUpdateTestCaseExecution() {
        log.info(name.getMethodName());
        String name = "testtestcaseexecution3";
        TestCaseExecutionDTO testcaseexecution = new TestCaseExecutionDTO();
        testcaseexecution.setDescription("testcasetestcaseexecutionDesc1");
        testcaseexecution = testCaseExecutionService.create(testcaseexecution);
        TestCaseExecutionDTO savedTestCaseExecution = testCaseExecutionService.read(testcaseexecution.getId());
        Assert.assertEquals("Returned testcaseexecution should have the new ID", testcaseexecution.getId(), savedTestCaseExecution.getId());

        name = "testcasetestcaseexecutionDesc2";
        testcaseexecution.setDescription(name);
        testcaseexecution = testCaseExecutionService.update(testcaseexecution);
        TestCaseExecutionDTO updatedTestCaseExecution = testCaseExecutionService.read(testcaseexecution.getId());
        Assert.assertTrue("Name of testcaseexecution should be updated", updatedTestCaseExecution.getDescription().equals(name));
    }

    @Test
    public void shouldReadTestCaseExecutionWithDeptOne() {
        log.info(name.getMethodName());
        TestCaseExecutionDTO testcaseexecution = new TestCaseExecutionDTO();
        testcaseexecution.setDescription("testcasetestcaseexecutionDesc1");

        List<TestStepExecutionDTO> testcases = new ArrayList<>();
        TestStepExecutionDTO testcase = new TestStepExecutionDTO();
        testcases.add(testcase);
        testcaseexecution.setTestStepExecutions(testcases);

        testcaseexecution = testCaseExecutionService.create(testcaseexecution);
        TestCaseExecutionDTO savedTestCaseExecution = testCaseExecutionService.read(testcaseexecution.getId());
        Assert.assertEquals("Returned testcaseexecution should have the new ID", testcaseexecution.getId(), savedTestCaseExecution.getId());
        Assert.assertTrue("TestCaseExecution list should be empty", savedTestCaseExecution.getTestStepExecutions().isEmpty());
    }

    @Test
    public void shouldReadTestCaseExecutionWithDeptTwo() {
        log.info(name.getMethodName());
        TestCaseExecutionDTO testcaseexecution = new TestCaseExecutionDTO();
        testcaseexecution.setDescription("testtestcaseexecution");

        List<TestStepExecutionDTO> testcases = new ArrayList<>();
        TestStepExecutionDTO testcase = new TestStepExecutionDTO();
        testcases.add(testcase);
        testcaseexecution.setTestStepExecutions(testcases);

        testcaseexecution = testCaseExecutionService.create(testcaseexecution);
        TestCaseExecutionDTO savedTestCaseExecution = testCaseExecutionService.readWithTestStepExecutions(testcaseexecution.getId());
        Assert.assertEquals("Returned testcaseexecution should have the new ID", testcaseexecution.getId(), savedTestCaseExecution.getId());
        Assert.assertFalse("TestCaseExecution list should not be empty", savedTestCaseExecution.getTestStepExecutions().isEmpty());
    }
}
