package org.theronin.nutcase.domain.execution.teststepexecution;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TestStepExecutionRepository extends JpaRepository<TestStepExecution, Long> {

}
