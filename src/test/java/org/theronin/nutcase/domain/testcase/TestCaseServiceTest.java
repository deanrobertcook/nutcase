package org.theronin.nutcase.domain.testcase;

import java.util.ArrayList;
import java.util.List;
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
import org.theronin.nutcase.domain.teststep.TestStepDTO;
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
        testCaseRepository.deleteAllInBatch();
        testStepRepository.deleteAllInBatch();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateNullTestCase() {
        log.info(name.getMethodName());
        testCaseService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateTestCaseWithID() {
        log.info(name.getMethodName());
        TestCaseDTO testcase = new TestCaseDTO();
        testcase.setId(Long.MAX_VALUE);
        testCaseService.create(testcase);
    }

    @Test
    public void shouldCreateTestCase() {
        log.info(name.getMethodName());
        TestCaseDTO testcase = new TestCaseDTO();
        testcase.setTestId(12L);
        testcase = testCaseService.create(testcase);
        TestCaseDTO savedTestCase = testCaseService.read(testcase.getId());
        Assert.assertEquals("Returned testcase should have the new ID", testcase.getId(), savedTestCase.getId());
    }

    @Test
    public void shouldDeleteTestCase() {
        log.info(name.getMethodName());
        TestCaseDTO testcase = new TestCaseDTO();
        testcase.setTestId(12L);
        testcase = testCaseService.create(testcase);
        TestCaseDTO savedTestCase = testCaseService.read(testcase.getId());
        Assert.assertEquals("Returned testcase should have the new ID", testcase.getId(), savedTestCase.getId());

        testCaseService.delete(testcase);
        TestCaseDTO deletedTestCase = testCaseService.read(testcase.getId());
        Assert.assertTrue("TestCase should not be found anymore", deletedTestCase == null);
    }

    @Test
    public void shouldUpdateTestCase() {
        log.info(name.getMethodName());
        TestCaseDTO testcase = new TestCaseDTO();
        testcase.setTestId(12L);
        testcase.setAutomated(true);
        testcase = testCaseService.create(testcase);
        TestCaseDTO savedTestCase = testCaseService.read(testcase.getId());
        Assert.assertEquals("Returned testcase should have the new ID", testcase.getId(), savedTestCase.getId());
        Assert.assertTrue("isAutomated of testcase should be set", savedTestCase.isAutomated() == true);

        testcase.setAutomated(false);
        testcase = testCaseService.update(testcase);
        TestCaseDTO updatedTestCase = testCaseService.read(testcase.getId());
        Assert.assertTrue("isAutomated of testcase should be updated", updatedTestCase.isAutomated() == false);
    }

    @Test
    public void shouldReadTestCaseWithDeptOne() {
        log.info(name.getMethodName());
        TestCaseDTO testcase = new TestCaseDTO();
        testcase.setTestId(12L);

        List<TestStepDTO> teststeps = new ArrayList<>();
        TestStepDTO teststep = new TestStepDTO();
        teststep.setDescription("teststepDesc1");
        teststeps.add(teststep);
        testcase.setTeststeps(teststeps);

        testcase = testCaseService.create(testcase);
        TestCaseDTO savedTestCase = testCaseService.read(testcase.getId());
        Assert.assertEquals("Returned testcase should have the new ID", testcase.getId(), savedTestCase.getId());
        Assert.assertTrue("Teststep list should be empty", savedTestCase.getTeststeps().isEmpty());
    }

    @Test
    public void shouldReadTestCaseWithDeptTwo() {
        log.info(name.getMethodName());
        TestCaseDTO testcase = new TestCaseDTO();
        testcase.setTestId(12L);

        List<TestStepDTO> teststeps = new ArrayList<>();
        TestStepDTO teststep = new TestStepDTO();
        teststep.setDescription("teststepDesc1");
        teststeps.add(teststep);
        testcase.setTeststeps(teststeps);

        testcase = testCaseService.create(testcase);
        TestCaseDTO savedTestCase = testCaseService.readWithSteps(testcase.getId());
        Assert.assertEquals("Returned testcase should have the new ID", testcase.getId(), savedTestCase.getId());
        Assert.assertFalse("Teststep list should not be empty", savedTestCase.getTeststeps().isEmpty());
    }

}
