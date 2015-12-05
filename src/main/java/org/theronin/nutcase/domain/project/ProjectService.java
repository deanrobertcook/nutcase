package org.theronin.nutcase.domain.project;

import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theronin.nutcase.config.logging.Logged;
import static org.theronin.nutcase.domain.base.MapDept.FLAT;
import static org.theronin.nutcase.domain.base.MapDept.FULL;
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
        return new ProjectDTO(getDefaultRepo().save(new Project(project, FULL)), FULL);
    }

    @Logged
    public void delete(ProjectDTO project) {
        notNull(project, new IllegalArgumentException("ProjectDTO is null"));
        notNull(project.getId(), new IllegalArgumentException("ProjectDTO ID should not be null"));
        getDefaultRepo().delete(new Project(project, FLAT));
    }

    @Logged
    public void delete(long id) {
        delete(read(id));
    }

    @Logged
    public ProjectDTO update(ProjectDTO project) {
        notNull(project, new IllegalArgumentException("ProjectDTO is null"));
        notNull(project.getId(), new IllegalArgumentException("ProjectDTO ID should not be null"));
        return new ProjectDTO(getDefaultRepo().save(new Project(project, FULL)), FULL);
    }

    @Logged
    public ProjectDTO read(long id) {
        Project entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new ProjectDTO(entity, FLAT);
    }

    @Logged
    public ProjectDTO readWithRunsAndTestcases(long id) {
        Project entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new ProjectDTO(entity, FULL);
    }
    
    @Logged
    public Page<ProjectDTO> readAll(Pageable pageable) {
        notNull(pageable, new IllegalArgumentException("Pageable is null"));
        Page<Project> page = getDefaultRepo().findAll(pageable);
        return page.map(p -> new ProjectDTO(p, FLAT));
    }
}
