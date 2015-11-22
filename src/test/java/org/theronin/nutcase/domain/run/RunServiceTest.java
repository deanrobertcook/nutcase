package org.theronin.nutcase.domain.run;

import javax.inject.Inject;
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
import org.theronin.nutcase.domain.base.ConstraintViolationBagException;
import org.theronin.nutcase.domain.testcase.TestCaseRepository;
import org.theronin.nutcase.domain.teststep.TestStepRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RunServiceTest {

    private static final Logger log = LoggerFactory.getLogger(RunServiceTest.class);

    @Rule
    public TestName name = new TestName();

    @Inject
    RunService runService;

    @Inject
    RunRepository runRepository;

    @Inject
    TestCaseRepository testCaseRepository;

    @Inject
    TestStepRepository testStepRepository;

    @Before
    public void initTest() {
        testStepRepository.deleteAllInBatch();
        testCaseRepository.deleteAllInBatch();
        runRepository.deleteAllInBatch();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateNullRun() {

        log.info(name.getMethodName());
        runService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateRunWithID() {
        log.info(name.getMethodName());
        Run run = new Run();
        run.setId(Long.MAX_VALUE);
        runService.create(run);
    }

    @Test(expected = ConstraintViolationBagException.class)
    public void shouldNotCreateInvalidRun() {
        log.info(name.getMethodName());
        Run run = new Run();
        runService.create(run);
    }

    @Test
    public void shouldCreateRun() {
        log.info(name.getMethodName());
        Run run = new Run();
        run.setName("testrun1");
        runService.create(run);
        Run savedRun = runService.read(run.getId());
        Assert.assertEquals("Returned run should have the new ID", run.getId(), savedRun.getId());
    }

    @Test
    public void shouldDeleteRun() {
        log.info(name.getMethodName());
        Run run = new Run();
        run.setName("testrun1");
        run = runService.create(run);
        Run savedRun = runService.read(run.getId());
        Assert.assertEquals("Returned run should have the new ID", run.getId(), savedRun.getId());

        runService.delete(run);
        Run deletedRun = runService.read(run.getId());
        Assert.assertTrue("Run should not be found anymore", deletedRun == null);
    }

    @Test
    public void shouldUpdateRun() {
        log.info(name.getMethodName());
        String name = "testrun3";
        Run run = new Run();
        run.setName(name);
        run = runService.create(run);
        Run savedRun = runService.read(run.getId());
        Assert.assertEquals("Returned run should have the new ID", run.getId(), savedRun.getId());

        name = "testrunUpdated";
        run.setName(name);
        runService.update(run);
        Run updatedRun = runService.read(run.getId());
        Assert.assertTrue("Name of run should be updated", updatedRun.getName().equals(name));
    }
}
