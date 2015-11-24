package org.theronin.nutcase.domain.teststep;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestStepServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TestStepServiceTest.class);

    @Rule
    public TestName name = new TestName();

    @Inject
    TestStepService testStepService;

    @Inject
    TestStepRepository testStepRepository;

    @Before
    public void initTest() {
        testStepRepository.deleteAllInBatch();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateNullTestStep() {

        log.info(name.getMethodName());
        testStepService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateTestStepWithID() {
        log.info(name.getMethodName());
        TestStep testStep = new TestStep();
        testStep.setId(Long.MAX_VALUE);
        testStepService.create(testStep);
    }

    @Test
    public void shouldCreateTestStep() {
        log.info(name.getMethodName());
        TestStep testStep = new TestStep();
        testStep = testStepService.create(testStep);
        TestStep savedTestStep = testStepService.read(testStep.getId());
        Assert.assertEquals("Returned testStep should have the new ID", testStep.getId(), savedTestStep.getId());
    }

    @Test
    public void shouldDeleteTestStep() {
        log.info(name.getMethodName());
        TestStep testStep = new TestStep();
        testStep = testStepService.create(testStep);
        TestStep savedTestStep = testStepService.read(testStep.getId());
        Assert.assertEquals("Returned testStep should have the new ID", testStep.getId(), savedTestStep.getId());

        testStepService.delete(testStep);
        TestStep deletedTestStep = testStepService.read(testStep.getId());
        Assert.assertTrue("TestStep should not be found anymore", deletedTestStep == null);
    }

    @Test
    public void shouldUpdateTestStep() {
        log.info(name.getMethodName());
        TestStep testStep = new TestStep();
        testStep.setStepNumber(1);
        testStep = testStepService.create(testStep);
        TestStep savedTestStep = testStepService.read(testStep.getId());
        Assert.assertEquals("Returned testStep should have the new ID", testStep.getId(), savedTestStep.getId());

        testStep.setStepNumber(2);
        testStep = testStepService.update(testStep);
        TestStep updatedTestStep = testStepService.read(testStep.getId());
        Assert.assertTrue("StepNumber of testStep should be updated", updatedTestStep.getStepNumber() == 2);
    }
}
