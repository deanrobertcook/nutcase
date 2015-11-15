package org.theronin.nutcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.theronin.nutcase.domain.TestCase;
import org.theronin.nutcase.domain.TestStep;

import java.util.Collection;

/**
 *
 */
public interface TestStepRepository extends JpaRepository<TestStep, Long> {
    Collection<TestStep> findByTestCase(TestCase testCase);
}
