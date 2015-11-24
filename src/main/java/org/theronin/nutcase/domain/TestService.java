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

		Run run = new Run(android, "Full RC", "Full RC for Android");

		runRepository.save(run);
		runRepository.save(new Run(android, "Calling", "Calling testrun for Android"));

		TestCase testCase1 = new TestCase("Test that the cursor works");
		TestCase testCase2 = new TestCase("Test that scrolling works");
		testCaseRepository.save(testCase1);
		testCaseRepository.save(testCase2);

		addSomeTestSteps(testCase1, testStepRepository, user1);
		addSomeTestSteps(testCase2, testStepRepository, user1);
//
//		android = projectRepository.findByName("Android").get();
//		System.out.println(android.toString());
//		System.out.println(android.getTestcases().size());
//		
//		System.out.println("All Test cases for project Android:");
//		for (TestCase testCase : android.getTestcases()) {
//			System.out.println(testCase.toString());
//		}
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
