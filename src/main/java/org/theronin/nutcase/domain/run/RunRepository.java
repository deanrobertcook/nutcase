package org.theronin.nutcase.domain.run;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RunRepository extends JpaRepository<Run, Long> {

    Optional<Run> findByName(String name);
}
