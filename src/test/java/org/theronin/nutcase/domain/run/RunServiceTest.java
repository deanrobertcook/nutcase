package org.theronin.nutcase.domain.run;

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
import org.theronin.nutcase.domain.execution.ExecutionDTO;
import org.theronin.nutcase.domain.execution.ExecutionRepository;
import org.theronin.nutcase.domain.project.ProjectRepository;
import org.theronin.nutcase.domain.testcase.TestCaseDTO;
import org.theronin.nutcase.domain.testcase.TestCaseRepository;
import org.theronin.nutcase.domain.teststep.TestStepRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RunServiceTest {

    private static final Logger log = LoggerFactory.getLogger(RunServiceTest.class);

    @Rule
    public TestName name = new TestName();

    @Inject
    private RunService runService;

    @Inject
    private RunRepository runRepository;

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private TestCaseRepository testCaseRepository;

    @Inject
    private TestStepRepository testStepRepository;

    @Inject
    private ExecutionRepository executionRepository;

    @Before
    public void initTest() {
        projectRepository.deleteAllInBatch();
        runRepository.deleteAllInBatch();
        testStepRepository.deleteAllInBatch();
        testCaseRepository.deleteAllInBatch();
        executionRepository.deleteAllInBatch();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateNullRun() {

        log.info(name.getMethodName());
        runService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateRunWithID() {
        log.info(name.getMethodName());
        RunDTO run = new RunDTO();
        run.setId(Long.MAX_VALUE);
        runService.create(run);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldNotCreateInvalidRun() {
        log.info(name.getMethodName());
        RunDTO run = new RunDTO();
        runService.create(run);
    }

    @Test
    public void shouldCreateRun() {
        log.info(name.getMethodName());
        RunDTO run = new RunDTO();
        run.setName("testrun1");
        run = runService.create(run);
        RunDTO savedRun = runService.read(run.getId());
        Assert.assertEquals("Returned run should have the new ID", run.getId(), savedRun.getId());
    }

    @Test
    public void shouldDeleteRun() {
        log.info(name.getMethodName());
        RunDTO run = new RunDTO();
        run.setName("testrun2");
        run = runService.create(run);
        RunDTO savedRun = runService.read(run.getId());
        Assert.assertEquals("Returned run should have the new ID", run.getId(), savedRun.getId());

        runService.delete(run);
        RunDTO deletedRun = runService.read(run.getId());
        Assert.assertTrue("Run should not be found anymore", deletedRun == null);
    }

    @Test
    public void shouldUpdateRun() {
        log.info(name.getMethodName());
        String name = "testrun3";
        RunDTO run = new RunDTO();
        run.setName(name);
        run = runService.create(run);
        RunDTO savedRun = runService.read(run.getId());
        Assert.assertEquals("Returned run should have the new ID", run.getId(), savedRun.getId());

        name = "testrunUpdated";
        run.setName(name);
        run = runService.update(run);
        RunDTO updatedRun = runService.read(run.getId());
        Assert.assertTrue("Name of run should be updated", updatedRun.getName().equals(name));
    }

    @Test
    public void shouldReadRunWithDeptOne() {
        log.info(name.getMethodName());
        RunDTO run = new RunDTO();
        run.setName("testrun");

        List<ExecutionDTO> executions = new ArrayList<>();
        ExecutionDTO execution = new ExecutionDTO();
        execution.setName("execution1");
        executions.add(execution);
        run.setExecutions(executions);

        List<TestCaseDTO> testcases = new ArrayList<>();
        TestCaseDTO testcase = new TestCaseDTO();
        testcase.setTestId(12L);
        testcases.add(testcase);
        run.setTestcases(testcases);

        run = runService.create(run);
        RunDTO savedRun = runService.read(run.getId());
        Assert.assertEquals("Returned run should have the new ID", run.getId(), savedRun.getId());
        Assert.assertTrue("Executions list should be empty", savedRun.getExecutions().isEmpty());
        Assert.assertTrue("Testcase list should be empty", savedRun.getTestcases().isEmpty());
    }

    @Test
    public void shouldReadRunWithDeptTwo() {
        log.info(name.getMethodName());
        RunDTO run = new RunDTO();
        run.setName("testrun");

        List<ExecutionDTO> executions = new ArrayList<>();
        ExecutionDTO execution = new ExecutionDTO();
        execution.setName("execution1");
        executions.add(execution);
        run.setExecutions(executions);

        List<TestCaseDTO> testcases = new ArrayList<>();
        TestCaseDTO testcase = new TestCaseDTO();
        testcase.setTestId(12L);
        testcases.add(testcase);
        run.setTestcases(testcases);

        run = runService.create(run);
        RunDTO savedRun = runService.readWithTestcasesAndExecutions(run.getId());
        Assert.assertEquals("Returned run should have the new ID", run.getId(), savedRun.getId());
        Assert.assertFalse("Executions list should not be empty", savedRun.getExecutions().isEmpty());
        Assert.assertFalse("Testcase list should not be empty", savedRun.getTestcases().isEmpty());
    }
}
