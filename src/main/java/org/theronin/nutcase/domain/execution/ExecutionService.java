package org.theronin.nutcase.domain.execution;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theronin.nutcase.config.logging.Logged;
import static org.theronin.nutcase.domain.base.EntityValidator.validateEntity;
import static org.theronin.nutcase.domain.base.ParameterValidator.isNull;
import static org.theronin.nutcase.domain.base.ParameterValidator.notNull;

@Service
@Transactional
public class ExecutionService {

    private final ExecutionRepository executionRepository;

    @Inject
    public ExecutionService(ExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }

    private ExecutionRepository getDefaultRepo() {
        return executionRepository;
    }

    @Logged
    public Execution createExecutionFromProject() {

        return null;
    }

    @Logged
    public Execution create(Execution execution) {
        notNull(execution, new IllegalArgumentException("Execution is null"));
        isNull(execution.getId(), new IllegalArgumentException("Execution ID should be null"));
        validateEntity(execution);
        return getDefaultRepo().save(execution);
    }

    @Logged
    public void delete(Execution execution) {
        notNull(execution, new IllegalArgumentException("Execution is null"));
        notNull(execution.getId(), new IllegalArgumentException("Execution ID should not be null"));
        getDefaultRepo().delete(execution);
    }

    @Logged
    public Execution update(Execution execution) {
        notNull(execution, new IllegalArgumentException("Execution is null"));
        notNull(execution.getId(), new IllegalArgumentException("Execution ID should not be null"));
        validateEntity(execution);
        return getDefaultRepo().save(execution);
    }

    @Logged
    public Execution read(Long id) {
        return getDefaultRepo().findOne(id);
    }
}
