package org.theronin.nutcase.domain.run;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theronin.nutcase.config.logging.Logged;
import static org.theronin.nutcase.domain.base.EntityValidator.validateEntity;
import static org.theronin.nutcase.domain.base.ParameterValidator.isNull;
import static org.theronin.nutcase.domain.base.ParameterValidator.notNull;

@Service
@Transactional
public class RunService {

    private final RunRepository runRepository;

    @Inject
    public RunService(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    private RunRepository getDefaultRepo() {
        return runRepository;
    }

    @Logged
    public RunDTO create(RunDTO run) {
        notNull(run, new IllegalArgumentException("Run is null"));
        isNull(run.getId(), new IllegalArgumentException("Run ID should be null"));
        Run entity = new Run(run, 2);
        validateEntity(entity);
        return new RunDTO(getDefaultRepo().save(entity), 2);
    }

    @Logged
    public void delete(RunDTO run) {
        notNull(run, new IllegalArgumentException("Run is null"));
        notNull(run.getId(), new IllegalArgumentException("Run ID should not be null"));
        Run entity = new Run(run, 1);
        getDefaultRepo().delete(entity);
    }

    @Logged
    public RunDTO update(RunDTO run) {
        notNull(run, new IllegalArgumentException("Run is null"));
        notNull(run.getId(), new IllegalArgumentException("Run ID should not be null"));
        Run entity = new Run(run, 2);
        validateEntity(entity);
        return new RunDTO(getDefaultRepo().save(entity), 2);
    }

    @Logged
    public RunDTO read(Long id) {
        notNull(id, new IllegalArgumentException("ID is null"));
        Run entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new RunDTO(entity, 1);
    }

    @Logged
    public RunDTO readWithTestcasesAndExecutions(Long id) {
        notNull(id, new IllegalArgumentException("ID is null"));
        Run entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new RunDTO(entity, 2);
    }
}
