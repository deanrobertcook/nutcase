package org.theronin.nutcase.domain.teststep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestStepRepository extends JpaRepository<TestStep, Long> {
}
