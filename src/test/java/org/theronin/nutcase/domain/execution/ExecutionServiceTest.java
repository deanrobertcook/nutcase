package org.theronin.nutcase.domain.execution;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.theronin.nutcase.Application;
import org.theronin.nutcase.domain.execution.testcaseexecution.TestCaseExecutionDTO;
import org.theronin.nutcase.domain.project.ProjectRepository;
import org.theronin.nutcase.domain.testcase.TestCaseRepository;
import org.theronin.nutcase.domain.teststep.TestStepRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ExecutionServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ExecutionServiceTest.class);

    @Rule
    public TestName name = new TestName();

    @Inject
    private ExecutionService executionService;

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
    public void shouldNotCreateNullRun() {

        log.info(name.getMethodName());
        executionService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateRunWithID() {
        log.info(name.getMethodName());
        ExecutionDTO execution = new ExecutionDTO();
        execution.setId(Long.MAX_VALUE);
        executionService.create(execution);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldNotCreateInvalidRun() {
        log.info(name.getMethodName());
        ExecutionDTO execution = new ExecutionDTO();
        executionService.create(execution);
    }

    @Test
    public void shouldCreateRun() {
        log.info(name.getMethodName());
        ExecutionDTO execution = new ExecutionDTO();
        execution.setName("testexecution1");
        execution = executionService.create(execution);
        ExecutionDTO savedRun = executionService.read(execution.getId());
        Assert.assertEquals("Returned execution should have the new ID", execution.getId(), savedRun.getId());
    }

    @Test
    public void shouldDeleteRun() {
        log.info(name.getMethodName());
        ExecutionDTO execution = new ExecutionDTO();
        execution.setName("testexecution2");
        execution = executionService.create(execution);
        ExecutionDTO savedRun = executionService.read(execution.getId());
        Assert.assertEquals("Returned execution should have the new ID", execution.getId(), savedRun.getId());

        executionService.delete(execution);
        ExecutionDTO deletedRun = executionService.read(execution.getId());
        Assert.assertTrue("Run should not be found anymore", deletedRun == null);
    }

    @Test
    public void shouldUpdateRun() {
        log.info(name.getMethodName());
        String name = "testexecution3";
        ExecutionDTO execution = new ExecutionDTO();
        execution.setName(name);
        execution = executionService.create(execution);
        ExecutionDTO savedRun = executionService.read(execution.getId());
        Assert.assertEquals("Returned execution should have the new ID", execution.getId(), savedRun.getId());

        name = "testexecutionUpdated";
        execution.setName(name);
        execution = executionService.update(execution);
        ExecutionDTO updatedRun = executionService.read(execution.getId());
        Assert.assertTrue("Name of execution should be updated", updatedRun.getName().equals(name));
    }

    @Test
    public void shouldReadRunWithDeptOne() {
        log.info(name.getMethodName());
        ExecutionDTO execution = new ExecutionDTO();
        execution.setName("testexecution");

        List<TestCaseExecutionDTO> testcases = new ArrayList<>();
        TestCaseExecutionDTO testcase = new TestCaseExecutionDTO();
        testcases.add(testcase);
        execution.setTestCaseExecutions(testcases);

        execution = executionService.create(execution);
        ExecutionDTO savedRun = executionService.read(execution.getId());
        Assert.assertEquals("Returned execution should have the new ID", execution.getId(), savedRun.getId());
        Assert.assertTrue("TestCaseExecution list should be empty", savedRun.getTestCaseExecutions().isEmpty());
    }

    @Test
    public void shouldReadRunWithDeptTwo() {
        log.info(name.getMethodName());
        ExecutionDTO execution = new ExecutionDTO();
        execution.setName("testexecution");

        List<TestCaseExecutionDTO> testcases = new ArrayList<>();
        TestCaseExecutionDTO testcase = new TestCaseExecutionDTO();
        testcases.add(testcase);
        execution.setTestCaseExecutions(testcases);

        execution = executionService.create(execution);
        ExecutionDTO savedRun = executionService.readWithTestCaseExecutions(execution.getId());
        Assert.assertEquals("Returned execution should have the new ID", execution.getId(), savedRun.getId());
        Assert.assertFalse("TestCaseExecution list should not be empty", savedRun.getTestCaseExecutions().isEmpty());
    }
}
