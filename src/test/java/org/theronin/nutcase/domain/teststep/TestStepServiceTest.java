package org.theronin.nutcase.domain.teststep;

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
import org.theronin.nutcase.domain.testcase.TestCaseRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestStepServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TestStepServiceTest.class);

    @Rule
    public TestName name = new TestName();

    @Inject
    private TestStepService testStepService;

    @Inject
    private TestStepRepository testStepRepository;

    @Inject
    private TestCaseRepository testCaseRepository;

    @Before
    public void initTest() {
        testCaseRepository.deleteAllInBatch();
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
        TestStepDTO testStep = new TestStepDTO();
        testStep.setId(Long.MAX_VALUE);
        testStepService.create(testStep);
    }

    @Ignore // nothing to validate yet
    @Test(expected = ConstraintViolationException.class)
    public void shouldNotCreateInvalidTestStep() {
        log.info(name.getMethodName());
        TestStepDTO testStep = new TestStepDTO();
        testStepService.create(testStep);
    }

    @Test
    public void shouldCreateTestStep() {
        log.info(name.getMethodName());
        TestStepDTO testStep = new TestStepDTO();
        testStep = testStepService.create(testStep);
        TestStepDTO savedTestStep = testStepService.read(testStep.getId());
        Assert.assertEquals("Returned testStep should have the new ID", testStep.getId(), savedTestStep.getId());
    }

    @Test
    public void shouldDeleteTestStep() {
        log.info(name.getMethodName());
        TestStepDTO testStep = new TestStepDTO();
        testStep = testStepService.create(testStep);
        TestStepDTO savedTestStep = testStepService.read(testStep.getId());
        Assert.assertEquals("Returned testStep should have the new ID", testStep.getId(), savedTestStep.getId());

        testStepService.delete(testStep);
        TestStepDTO deletedTestStep = testStepService.read(testStep.getId());
        Assert.assertTrue("TestStep should not be found anymore", deletedTestStep == null);
    }

    @Test
    public void shouldUpdateTestStep() {
        log.info(name.getMethodName());
        TestStepDTO testStep = new TestStepDTO();
        testStep.setDescription("stepDesc1");
        testStep = testStepService.create(testStep);
        TestStepDTO savedTestStep = testStepService.read(testStep.getId());
        Assert.assertEquals("Returned testStep should have the new ID", testStep.getId(), savedTestStep.getId());
        Assert.assertTrue("Description of testStep should be set", savedTestStep.getDescription().equals("stepDesc1"));

        testStep.setDescription("stepDesc2");
        testStep = testStepService.update(testStep);
        TestStepDTO updatedTestStep = testStepService.read(testStep.getId());
        Assert.assertTrue("Description of testStep should be updated", updatedTestStep.getDescription().equals("stepDesc2"));
    }

}
