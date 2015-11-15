package org.theronin.nutcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.theronin.nutcase.domain.Project;

import java.util.Optional;

/**
 *
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByName(String name);
}
