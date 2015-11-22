package org.theronin.nutcase.domain.execution;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {

    Optional<Execution> findByName(String name);
}
