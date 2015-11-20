package org.theronin.nutcase.domain;

import org.springframework.stereotype.Service;
import org.theronin.nutcase.config.logging.Logged;
import org.theronin.nutcase.domain.project.Project;
import org.theronin.nutcase.domain.project.ProjectRepository;
import org.theronin.nutcase.domain.testcase.TestCase;
import org.theronin.nutcase.domain.testcase.TestCaseRepository;
import org.theronin.nutcase.domain.teststep.TestStep;
import org.theronin.nutcase.domain.teststep.TestStepRepository;
import org.theronin.nutcase.domain.user.NutcaseUser;
import org.theronin.nutcase.domain.user.NutcaseUserRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Service
public class TestService {

    @Inject
    TestStepRepository testStepRepository;

    @Inject
    TestCaseRepository testCaseRepository;

    @Inject
    ProjectRepository projectRepository;

    @Inject
    NutcaseUserRepository userRepository;

    @Logged
    @PostConstruct
    public void init() {
        testStepRepository.deleteAllInBatch();
        testCaseRepository.deleteAllInBatch();
        projectRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        NutcaseUser user1 = new NutcaseUser("test1@wire.com", "password");
        NutcaseUser user2 = new NutcaseUser("test2@wire.com", "password");

        userRepository.save(user1);
        userRepository.save(user2);

        Project someProject = new Project("Android", "The Android project.");

        projectRepository.save(someProject);
        projectRepository.save(new Project("iOS", "The iOS project."));
        projectRepository.save(new Project("WebApp", "The WebApp project."));

//        TestCase testCase1 = new TestCase(someProject, "Test that the cursor works");
//        TestCase testCase2 = new TestCase(someProject, "Test that scrolling works");
//        testCaseRepository.save(testCase1);
//        testCaseRepository.save(testCase2);
//
//        addSomeTestSteps(testCase1, testStepRepository, user1);
//        addSomeTestSteps(testCase2, testStepRepository, user1);
//
//        System.out.println("All Test cases for project Android:");
//        for (TestCase testCase : testCaseRepository.findByRunName("Android")) {
//            System.out.println(testCase.toString());
//        }
    }

    @Logged
    private void addSomeTestSteps(TestCase testCase, TestStepRepository testStepRepository, NutcaseUser user) {
        testStepRepository.save(new TestStep(testCase, 1, "Do something to: " + testCase.getDescription()));
        testStepRepository.save(new TestStep(testCase, 2, "Do something to: " + testCase.getDescription()));
        testStepRepository.save(new TestStep(testCase, 3, "Do something to: " + testCase.getDescription()));
        testStepRepository.save(new TestStep(testCase, 4, "Do something to: " + testCase.getDescription()));
        testStepRepository.save(new TestStep(testCase, 5, "Do something to: " + testCase.getDescription()));
    }

}
