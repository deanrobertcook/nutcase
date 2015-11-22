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
    public Run create(Run run) {
        notNull(run, new IllegalArgumentException("Run is null"));
        isNull(run.getId(), new IllegalArgumentException("Run ID should be null"));
        validateEntity(run);
        return getDefaultRepo().save(run);
    }

    @Logged
    public void delete(Run run) {
        notNull(run, new IllegalArgumentException("Run is null"));
        notNull(run.getId(), new IllegalArgumentException("Run ID should not be null"));
        getDefaultRepo().delete(run);
    }

    @Logged
    public Run update(Run run) {
        notNull(run, new IllegalArgumentException("Run is null"));
        notNull(run.getId(), new IllegalArgumentException("Run ID should not be null"));
        validateEntity(run);
        return getDefaultRepo().save(run);
    }

    @Logged
    public Run read(Long id) {
        return getDefaultRepo().findOne(id);
    }
}
