package org.theronin.nutcase.domain.teststep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.theronin.nutcase.domain.testcase.TestCase;

import java.util.Collection;
import org.springframework.stereotype.Repository;

@Repository
public interface TestStepRepository extends JpaRepository<TestStep, Long> {

    Collection<TestStep> findByTestCase(TestCase testCase);
}
