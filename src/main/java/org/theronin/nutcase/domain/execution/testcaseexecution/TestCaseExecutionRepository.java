package org.theronin.nutcase.domain.execution.testcaseexecution;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseExecutionRepository extends JpaRepository<TestCaseExecution, Long> {

}
