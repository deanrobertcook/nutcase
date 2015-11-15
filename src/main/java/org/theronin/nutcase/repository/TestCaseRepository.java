package org.theronin.nutcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.theronin.nutcase.domain.TestCase;

import java.util.Collection;

/**
 *
 */
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    Collection<TestCase> findByProjectName(String name);
}
