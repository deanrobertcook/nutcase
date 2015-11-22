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
    public Project create(Project project) {
        notNull(project, new IllegalArgumentException("Project is null"));
        isNull(project.getId(), new IllegalArgumentException("Project ID should be null"));
        validateEntity(project);
        return getDefaultRepo().save(project);
    }

    @Logged
    public void delete(Project project) {
        notNull(project, new IllegalArgumentException("Project is null"));
        notNull(project.getId(), new IllegalArgumentException("Project ID should not be null"));
        getDefaultRepo().delete(project);
    }

    @Logged
    public Project update(Project project) {
        notNull(project, new IllegalArgumentException("Project is null"));
        notNull(project.getId(), new IllegalArgumentException("Project ID should not be null"));
        validateEntity(project);
        return getDefaultRepo().save(project);
    }

    @Logged
    public Project read(Long id) {
        return getDefaultRepo().findOne(id);
    }
}
