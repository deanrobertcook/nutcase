package org.theronin.nutcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.theronin.nutcase.domain.NutcaseUser;
import org.theronin.nutcase.domain.Project;
import org.theronin.nutcase.domain.TestCase;
import org.theronin.nutcase.domain.TestStep;
import org.theronin.nutcase.repository.NutcaseUserRepository;
import org.theronin.nutcase.repository.ProjectRepository;
import org.theronin.nutcase.repository.TestCaseRepository;
import org.theronin.nutcase.repository.TestStepRepository;

/**
 *
 */
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner bookmarks(NutcaseUserRepository nutcaseUserRepository,
                                       ProjectRepository projectRepository,
                                       TestCaseRepository testCaseRepository,
                                       TestStepRepository testStepRepository) {
        return args -> {

            testStepRepository.deleteAllInBatch();
            testCaseRepository.deleteAllInBatch();
            projectRepository.deleteAllInBatch();
            nutcaseUserRepository.deleteAllInBatch();

            NutcaseUser user1 = new NutcaseUser("test1@wire.com", "password", 0);
            NutcaseUser user2 = new NutcaseUser("test2@wire.com", "password", 0);


            nutcaseUserRepository.save(user1);
            nutcaseUserRepository.save(user2);

            Project someProject = new Project(user1, "Android", "The Android project.");

            projectRepository.save(someProject);
            projectRepository.save(new Project(user1, "iOS", "The iOS project."));
            projectRepository.save(new Project(user2, "WebApp", "The WebApp project."));

            TestCase testCase1 = new TestCase(someProject, user1, "Test that the cursor works");
            TestCase testCase2 = new TestCase(someProject, user1, "Test that scrolling works");
            testCaseRepository.save(testCase1);
            testCaseRepository.save(testCase2);

            addSomeTestSteps(testCase1, testStepRepository, user1);
            addSomeTestSteps(testCase2, testStepRepository, user1);


            System.out.println("All Test cases for project Android:");
            for (TestCase testCase : testCaseRepository.findByProjectName("Android")) {
                System.out.println(testCase.toString());
            }
        };
    }

    private void addSomeTestSteps(TestCase testCase, TestStepRepository testStepRepository, NutcaseUser user) {
        testStepRepository.save(new TestStep(testCase, user, 1, "Do something to: " + testCase.getDescription()));
        testStepRepository.save(new TestStep(testCase, user, 2, "Do something to: " + testCase.getDescription()));
        testStepRepository.save(new TestStep(testCase, user, 3, "Do something to: " + testCase.getDescription()));
        testStepRepository.save(new TestStep(testCase, user, 4, "Do something to: " + testCase.getDescription()));
        testStepRepository.save(new TestStep(testCase, user, 5, "Do something to: " + testCase.getDescription()));
    }

}
