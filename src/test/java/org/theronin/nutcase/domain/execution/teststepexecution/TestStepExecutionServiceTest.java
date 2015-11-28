package org.theronin.nutcase.domain.execution.teststepexecution;

import javax.inject.Inject;
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
import org.theronin.nutcase.domain.base.ConstraintViolationBagException;
import org.theronin.nutcase.domain.execution.ExecutionRepository;
import org.theronin.nutcase.domain.project.ProjectRepository;
import org.theronin.nutcase.domain.testcase.TestCaseRepository;
import org.theronin.nutcase.domain.teststep.TestStepRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestStepExecutionServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TestStepExecutionServiceTest.class);

    @Rule
    public TestName name = new TestName();

    @Inject
    TestStepExecutionService testStepExecutionService;

    @Inject
    ExecutionRepository testStepExecutionRepository;

    @Inject
    ProjectRepository projectRepository;

    @Inject
    TestCaseRepository testCaseRepository;

    @Inject
    TestStepRepository testStepRepository;

    @Before
    public void initTest() {
        projectRepository.deleteAllInBatch();
        testStepExecutionRepository.deleteAllInBatch();
        testCaseRepository.deleteAllInBatch();
        testStepRepository.deleteAllInBatch();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateNullTestStepExecution() {

        log.info(name.getMethodName());
        testStepExecutionService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateTestStepExecutionWithID() {
        log.info(name.getMethodName());
        TestStepExecutionDTO testStepExecution = new TestStepExecutionDTO();
        testStepExecution.setId(Long.MAX_VALUE);
        testStepExecutionService.create(testStepExecution);
    }

    @Ignore // nothing to validate yet
    @Test(expected = ConstraintViolationBagException.class)
    public void shouldNotCreateInvalidTestStepExecution() {
        log.info(name.getMethodName());
        TestStepExecutionDTO testStepExecution = new TestStepExecutionDTO();
        testStepExecutionService.create(testStepExecution);
    }

    @Test
    public void shouldCreateTestStepExecution() {
        log.info(name.getMethodName());
        TestStepExecutionDTO testStepExecution = new TestStepExecutionDTO();
        testStepExecution.setDescription("testtestStepExecution1");
        testStepExecution = testStepExecutionService.create(testStepExecution);
        TestStepExecutionDTO savedTestStepExecution = testStepExecutionService.read(testStepExecution.getId());
        Assert.assertEquals("Returned testStepExecution should have the new ID", testStepExecution.getId(), savedTestStepExecution.getId());
    }

    @Test
    public void shouldDeleteTestStepExecution() {
        log.info(name.getMethodName());
        TestStepExecutionDTO testStepExecution = new TestStepExecutionDTO();
        testStepExecution.setDescription("testtestStepExecution2");
        testStepExecution = testStepExecutionService.create(testStepExecution);
        TestStepExecutionDTO savedTestStepExecution = testStepExecutionService.read(testStepExecution.getId());
        Assert.assertEquals("Returned testStepExecution should have the new ID", testStepExecution.getId(), savedTestStepExecution.getId());

        testStepExecutionService.delete(testStepExecution);
        TestStepExecutionDTO deletedTestStepExecution = testStepExecutionService.read(testStepExecution.getId());
        Assert.assertTrue("TestStepExecution should not be found anymore", deletedTestStepExecution == null);
    }

    @Test
    public void shouldUpdateTestStepExecution() {
        log.info(name.getMethodName());
        String name = "testtestStepExecution3";
        TestStepExecutionDTO testStepExecution = new TestStepExecutionDTO();
        testStepExecution.setDescription(name);
        testStepExecution = testStepExecutionService.create(testStepExecution);
        TestStepExecutionDTO savedTestStepExecution = testStepExecutionService.read(testStepExecution.getId());
        Assert.assertEquals("Returned testStepExecution should have the new ID", testStepExecution.getId(), savedTestStepExecution.getId());

        name = "testtestStepExecutionUpdated";
        testStepExecution.setDescription(name);
        testStepExecution = testStepExecutionService.update(testStepExecution);
        TestStepExecutionDTO updatedTestStepExecution = testStepExecutionService.read(testStepExecution.getId());
        Assert.assertTrue("Name of testStepExecution should be updated", updatedTestStepExecution.getDescription().equals(name));
    }

}
