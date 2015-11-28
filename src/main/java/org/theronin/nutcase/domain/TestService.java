package org.theronin.nutcase.domain;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theronin.nutcase.config.logging.Logged;
import org.theronin.nutcase.domain.project.Project;
import org.theronin.nutcase.domain.project.ProjectRepository;
import org.theronin.nutcase.domain.run.Run;
import org.theronin.nutcase.domain.run.RunRepository;
import org.theronin.nutcase.domain.testcase.TestCase;
import org.theronin.nutcase.domain.testcase.TestCaseRepository;
import org.theronin.nutcase.domain.teststep.TestStep;
import org.theronin.nutcase.domain.teststep.TestStepRepository;
import org.theronin.nutcase.domain.user.NutcaseUser;
import org.theronin.nutcase.domain.user.NutcaseUserRepository;

@Service
@Transactional
public class TestService {

	@Inject
	TestStepRepository testStepRepository;

	@Inject
	TestCaseRepository testCaseRepository;

	@Inject
	RunRepository runRepository;

	@Inject
	ProjectRepository projectRepository;

	@Inject
	NutcaseUserRepository userRepository;

	@Logged
	@PostConstruct
	public void init() {
		
		testStepRepository.deleteAllInBatch();
		runRepository.deleteAllInBatch();
		testCaseRepository.deleteAllInBatch();
		projectRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();

		NutcaseUser user1 = new NutcaseUser("test1@wire.com", "password");
		NutcaseUser user2 = new NutcaseUser("test2@wire.com", "password");

		userRepository.save(user1);
		userRepository.save(user2);

		Project android = new Project("Android", "The Android project.");
		android = projectRepository.save(android);

        Run run1 = new Run("Full RC", "Full RC for Android");
        Run run2 = new Run("Calling", "Calling testrun for Android");

        runRepository.save(run1);
        runRepository.save(run2);
        android.getRuns().add(run1);
        android.getRuns().add(run2);
        android = projectRepository.save(android);

        TestCase testCase1 = new TestCase(12L, "Test that the cursor works");
        TestCase testCase2 = new TestCase(13L, "Test that scrolling works");
		testCaseRepository.save(testCase1);
		testCaseRepository.save(testCase2);

        addSomeTestSteps(testCase1, testCaseRepository, user1);
        addSomeTestSteps(testCase2, testCaseRepository, user1);
	}

	@Logged
    private void addSomeTestSteps(TestCase testCase, TestCaseRepository testCaseRepository, NutcaseUser user) {
        TestStep testStep1 = new TestStep("Do something to: " + testCase.getDescription());
        TestStep testStep2 = new TestStep("Do something to: " + testCase.getDescription());
        TestStep testStep3 = new TestStep("Do something to: " + testCase.getDescription());
        TestStep testStep4 = new TestStep("Do something to: " + testCase.getDescription());
        TestStep testStep5 = new TestStep("Do something to: " + testCase.getDescription());

        testCase.getTeststeps().add(testStep1);
        testCase.getTeststeps().add(testStep2);
        testCase.getTeststeps().add(testStep3);
        testCase.getTeststeps().add(testStep4);
        testCase.getTeststeps().add(testStep5);

        testCaseRepository.save(testCase);
    }

}
