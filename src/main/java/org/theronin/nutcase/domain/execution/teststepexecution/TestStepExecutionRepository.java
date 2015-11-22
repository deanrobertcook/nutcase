package org.theronin.nutcase.domain.execution.teststepexecution;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import org.theronin.nutcase.domain.execution.testcaseexecution.TestCaseExecution;

@Repository
public interface TestStepExecutionRepository extends JpaRepository<TestStepExecution, Long> {

    Collection<TestStepExecution> findByTestCaseExecution(TestCaseExecution testCaseExecution);
}
