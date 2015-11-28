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
    public ExecutionDTO create(ExecutionDTO execution) {
        notNull(execution, new IllegalArgumentException("Execution is null"));
        isNull(execution.getId(), new IllegalArgumentException("Execution ID should be null"));
        Execution entity = new Execution(execution, 2);
        validateEntity(entity);
        return new ExecutionDTO(getDefaultRepo().save(entity), 2);
    }

    @Logged
    public void delete(ExecutionDTO execution) {
        notNull(execution, new IllegalArgumentException("Execution is null"));
        notNull(execution.getId(), new IllegalArgumentException("Execution ID should not be null"));
        Execution entity = new Execution(execution, 1);
        getDefaultRepo().delete(entity);
    }

    @Logged
    public ExecutionDTO update(ExecutionDTO execution) {
        notNull(execution, new IllegalArgumentException("Execution is null"));
        notNull(execution.getId(), new IllegalArgumentException("Execution ID should not be null"));
        Execution entity = new Execution(execution, 2);
        validateEntity(entity);
        return new ExecutionDTO(getDefaultRepo().save(entity), 2);
    }

    @Logged
    public ExecutionDTO read(Long id) {
        Execution entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new ExecutionDTO(entity, 1);
    }

    @Logged
    public ExecutionDTO readWithTestCaseExecutions(Long id) {
        Execution entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new ExecutionDTO(entity, 2);
    }
}
