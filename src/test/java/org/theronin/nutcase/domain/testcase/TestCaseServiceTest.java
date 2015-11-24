package org.theronin.nutcase.domain.testcase;

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
import org.theronin.nutcase.domain.project.ProjectService;
import org.theronin.nutcase.domain.teststep.TestStepRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
//@Transactional
public class TestCaseServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TestCaseServiceTest.class);

    @Rule
    public TestName name = new TestName();

    @Inject
    TestCaseService testCaseService;

    @Inject
    ProjectService projectService;

    @Inject
    TestCaseRepository testCaseRepository;

    @Inject
    TestStepRepository testStepRepository;

    @Before
    public void initTest() {
        testStepRepository.deleteAllInBatch();
        testCaseRepository.deleteAllInBatch();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateNullTestCase() {
        log.info(name.getMethodName());
        testCaseService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateTestCaseWithID() {
        log.info(name.getMethodName());
        TestCase testcase = new TestCase();
        testcase.setId(Long.MAX_VALUE);
        testCaseService.create(testcase);
    }

    @Test
    public void shouldCreateTestCase() {
        log.info(name.getMethodName());
        TestCase testcase = new TestCase();
        testcase = testCaseService.create(testcase);
        TestCase savedTestCase = testCaseService.read(testcase.getId());
        Assert.assertEquals("Returned testcase should have the new ID", testcase.getId(), savedTestCase.getId());
    }

    @Test
    public void shouldDeleteTestCase() {
        log.info(name.getMethodName());
        TestCase testcase = new TestCase();
        testcase = testCaseService.create(testcase);
        TestCase savedTestCase = testCaseService.read(testcase.getId());
        Assert.assertEquals("Returned testcase should have the new ID", testcase.getId(), savedTestCase.getId());

        testCaseService.delete(testcase);
        TestCase deletedTestCase = testCaseService.read(testcase.getId());
        Assert.assertTrue("TestCase should not be found anymore", deletedTestCase == null);
    }

    @Test
    public void shouldUpdateTestCase() {
        log.info(name.getMethodName());
        TestCase testcase = new TestCase();
        testcase.setAutomated(true);
        testcase = testCaseService.create(testcase);
        TestCase savedTestCase = testCaseService.read(testcase.getId());
        Assert.assertEquals("Returned testcase should have the new ID", testcase.getId(), savedTestCase.getId());

        testcase.setAutomated(false);
        testcase = testCaseService.update(testcase);
        TestCase updatedTestCase = testCaseService.read(testcase.getId());
        Assert.assertTrue("isAutomated of testcase should be updated", updatedTestCase.isAutomated() == false);
    }

}
