package org.theronin.nutcase.domain.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.theronin.nutcase.domain.project.Project;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

		Optional<Project> findByName(String name);
}
