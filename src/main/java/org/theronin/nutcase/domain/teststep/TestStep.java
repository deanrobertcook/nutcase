package org.theronin.nutcase.domain.teststep;

import org.theronin.nutcase.domain.testcase.TestCase;
import javax.persistence.*;

/**
 *
 */
@Entity
public class TestStep {

		@Id
		@GeneratedValue
		private Long id;

		@ManyToOne
		private TestCase testCase;

		private int stepNumber;

		private String description;

		protected TestStep() {
		}

		public TestStep(TestCase testCase, int stepNumber, String description) {
				this.testCase = testCase;
				this.stepNumber = stepNumber;
				this.description = description;
		}

		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}

		public TestCase getTestCase() {
				return testCase;
		}

		public void setTestCase(TestCase testCase) {
				this.testCase = testCase;
		}

		public int getStepNumber() {
				return stepNumber;
		}

		public void setStepNumber(int stepNumber) {
				this.stepNumber = stepNumber;
		}

		public String getDescription() {
				return description;
		}

		public void setDescription(String description) {
				this.description = description;
		}
}
