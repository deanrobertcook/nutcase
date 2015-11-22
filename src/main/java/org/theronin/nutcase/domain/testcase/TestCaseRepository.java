package org.theronin.nutcase.domain.testcase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    Collection<TestCase> findByRunName(String name);
}
