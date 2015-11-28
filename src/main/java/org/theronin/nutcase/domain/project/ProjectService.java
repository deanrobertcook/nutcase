package org.theronin.nutcase.domain.project;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theronin.nutcase.config.logging.Logged;
import static org.theronin.nutcase.domain.base.EntityValidator.validateEntity;
import static org.theronin.nutcase.domain.base.ParameterValidator.isNull;
import static org.theronin.nutcase.domain.base.ParameterValidator.notNull;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Inject
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    private ProjectRepository getDefaultRepo() {
        return projectRepository;
    }

    @Logged
    public ProjectDTO create(ProjectDTO project) {
        notNull(project, new IllegalArgumentException("ProjectDTO is null"));
        isNull(project.getId(), new IllegalArgumentException("ProjectDTO ID should be null"));
        Project entity = new Project(project, 2);
        validateEntity(entity);
        return new ProjectDTO(getDefaultRepo().save(entity), 2);
    }

    @Logged
    public void delete(ProjectDTO project) {
        notNull(project, new IllegalArgumentException("ProjectDTO is null"));
        notNull(project.getId(), new IllegalArgumentException("ProjectDTO ID should not be null"));
        Project entity = new Project(project, 1);
        getDefaultRepo().delete(entity);
    }

    @Logged
    public ProjectDTO update(ProjectDTO project) {
        notNull(project, new IllegalArgumentException("ProjectDTO is null"));
        notNull(project.getId(), new IllegalArgumentException("ProjectDTO ID should not be null"));
        Project entity = new Project(project, 2);
        validateEntity(entity);
        return new ProjectDTO(getDefaultRepo().save(entity), 2);
    }

    @Logged
    public ProjectDTO read(Long id) {
        Project entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new ProjectDTO(entity, 1);
    }

    @Logged
    public ProjectDTO readWithRunsAndTestcases(Long id) {
        Project entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new ProjectDTO(entity, 2);
    }
}
