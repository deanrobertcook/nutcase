package org.theronin.nutcase.domain.project;

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
import org.theronin.nutcase.domain.base.ConstraintViolationBagException;
import org.theronin.nutcase.domain.run.RunDTO;
import org.theronin.nutcase.domain.run.RunRepository;
import org.theronin.nutcase.domain.testcase.TestCaseDTO;
import org.theronin.nutcase.domain.testcase.TestCaseRepository;
import org.theronin.nutcase.domain.teststep.TestStepRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ProjectServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ProjectServiceTest.class);

    @Rule
    public TestName name = new TestName();

    @Inject
    ProjectService projectService;

    @Inject
    ProjectRepository projectRepository;

    @Inject
    RunRepository runRepository;

    @Inject
    TestCaseRepository testCaseRepository;

    @Inject
    TestStepRepository testStepRepository;

    @Before
    public void initTest() {
        projectRepository.deleteAllInBatch();
        testCaseRepository.deleteAllInBatch();
        testStepRepository.deleteAllInBatch();
        runRepository.deleteAllInBatch();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateNullProject() {

        log.info(name.getMethodName());
        projectService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateProjectWithID() {
        log.info(name.getMethodName());
        ProjectDTO project = new ProjectDTO();
        project.setId(Long.MAX_VALUE);
        projectService.create(project);
    }

    @Test(expected = ConstraintViolationBagException.class)
    public void shouldNotCreateInvalidProject() {
        log.info(name.getMethodName());
        ProjectDTO project = new ProjectDTO();
        projectService.create(project);
    }

    @Test
    public void shouldCreateProject() {
        log.info(name.getMethodName());
        ProjectDTO project = new ProjectDTO();
        project.setName("testproject");
        project = projectService.create(project);
        ProjectDTO savedProject = projectService.read(project.getId());
        Assert.assertEquals("Returned project should have the new ID", project.getId(), savedProject.getId());
    }

    @Test
    public void shouldDeleteProject() {
        log.info(name.getMethodName());
        ProjectDTO project = new ProjectDTO();
        project.setName("testproject");
        project = projectService.create(project);
        ProjectDTO savedProject = projectService.read(project.getId());
        Assert.assertEquals("Returned project should have the new ID", project.getId(), savedProject.getId());

        projectService.delete(project);
        ProjectDTO deletedProject = projectService.read(project.getId());
        Assert.assertTrue("Project should not be found anymore", deletedProject == null);
    }

    @Test
    public void shouldUpdateProject() {
        log.info(name.getMethodName());
        String name = "testproject";
        ProjectDTO project = new ProjectDTO();
        project.setName(name);
        project = projectService.create(project);
        ProjectDTO savedProject = projectService.read(project.getId());
        Assert.assertEquals("Returned project should have the new ID", project.getId(), savedProject.getId());

        name = "testprojectUpdated";
        project.setName(name);
        project = projectService.update(project);
        ProjectDTO updatedProject = projectService.read(project.getId());
        Assert.assertTrue("Name of project should be updated", updatedProject.getName().equals(name));
    }

    @Test
    public void shouldReadProjectWithDeptOne() {
        log.info(name.getMethodName());
        ProjectDTO project = new ProjectDTO();
        project.setName("testproject");

        List<RunDTO> runs = new ArrayList<>();
        RunDTO run = new RunDTO();
        run.setName("run1");
        runs.add(run);
        project.setRuns(runs);

        List<TestCaseDTO> testcases = new ArrayList<>();
        TestCaseDTO testcase = new TestCaseDTO();
        testcase.setTestId(12L);
        testcases.add(testcase);
        project.setTestcases(testcases);

        project = projectService.create(project);
        ProjectDTO savedProject = projectService.read(project.getId());
        Assert.assertEquals("Returned project should have the new ID", project.getId(), savedProject.getId());
        Assert.assertTrue("Run list should be empty", savedProject.getRuns().isEmpty());
        Assert.assertTrue("Testcase list should be empty", savedProject.getTestcases().isEmpty());
    }

    @Test
    public void shouldReadProjectWithDeptTwo() {
        log.info(name.getMethodName());
        ProjectDTO project = new ProjectDTO();
        project.setName("testproject");

        List<RunDTO> runs = new ArrayList<>();
        RunDTO run = new RunDTO();
        run.setName("run1");
        runs.add(run);
        project.setRuns(runs);

        List<TestCaseDTO> testcases = new ArrayList<>();
        TestCaseDTO testcase = new TestCaseDTO();
        testcase.setTestId(12L);
        testcases.add(testcase);
        project.setTestcases(testcases);

        project = projectService.create(project);
        ProjectDTO savedProject = projectService.readWithRunsAndTestcases(project.getId());
        Assert.assertEquals("Returned project should have the new ID", project.getId(), savedProject.getId());
        Assert.assertFalse("Run list should not be empty", savedProject.getRuns().isEmpty());
        Assert.assertFalse("Testcase list should not be empty", savedProject.getTestcases().isEmpty());
    }
}
