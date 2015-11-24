package org.theronin.nutcase.domain.project;

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
import org.theronin.nutcase.domain.run.RunRepository;
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
        testStepRepository.deleteAllInBatch();
        testCaseRepository.deleteAllInBatch();
        runRepository.deleteAllInBatch();
        projectRepository.deleteAllInBatch();

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateNullProject() {

        log.info(name.getMethodName());
        projectService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateProjectWithID() {
        log.info(name.getMethodName());
        Project project = new Project();
        project.setId(Long.MAX_VALUE);
        projectService.create(project);
    }

    @Test(expected = ConstraintViolationBagException.class)
    public void shouldNotCreateInvalidProject() {
        log.info(name.getMethodName());
        Project project = new Project();
        projectService.create(project);
    }

    @Test
    public void shouldCreateProject() {
        log.info(name.getMethodName());
        Project project = new Project();
        project.setName("testproject");
        projectService.create(project);
        Project savedProject = projectService.read(project.getId());
        Assert.assertEquals("Returned project should have the new ID", project.getId(), savedProject.getId());
    }

    @Test
    public void shouldDeleteProject() {
        log.info(name.getMethodName());
        Project project = new Project();
        project.setName("testproject");
        project = projectService.create(project);
        Project savedProject = projectService.read(project.getId());
        Assert.assertEquals("Returned project should have the new ID", project.getId(), savedProject.getId());

        projectService.delete(project);
        Project deletedProject = projectService.read(project.getId());
        Assert.assertTrue("Project should not be found anymore", deletedProject == null);
    }

    @Test
    public void shouldUpdateProject() {
        log.info(name.getMethodName());
        String name = "testproject";
        Project project = new Project();
        project.setName(name);
        project = projectService.create(project);
        Project savedProject = projectService.read(project.getId());
        Assert.assertEquals("Returned project should have the new ID", project.getId(), savedProject.getId());

        name = "testprojectUpdated";
        project.setName(name);
        project = projectService.update(project);
        Project updatedProject = projectService.read(project.getId());
        Assert.assertTrue("Name of project should be updated", updatedProject.getName().equals(name));
    }
}
