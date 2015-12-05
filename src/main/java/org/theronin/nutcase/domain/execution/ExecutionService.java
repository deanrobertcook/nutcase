package org.theronin.nutcase.domain.execution;

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
    public ExecutionDTO create(ExecutionDTO execution) {
        notNull(execution, new IllegalArgumentException("Execution is null"));
        isNull(execution.getId(), new IllegalArgumentException("Execution ID should be null"));
        return new ExecutionDTO(getDefaultRepo().save(new Execution(execution, FULL)), FULL);
    }

    @Logged
    public void delete(ExecutionDTO execution) {
        notNull(execution, new IllegalArgumentException("Execution is null"));
        notNull(execution.getId(), new IllegalArgumentException("Execution ID should not be null"));
        getDefaultRepo().delete(new Execution(execution, FLAT));
    }

    @Logged
    public void delete(long id) {
        delete(read(id));
    }

    @Logged
    public ExecutionDTO update(ExecutionDTO execution) {
        notNull(execution, new IllegalArgumentException("Execution is null"));
        notNull(execution.getId(), new IllegalArgumentException("Execution ID should not be null"));
        return new ExecutionDTO(getDefaultRepo().save(new Execution(execution, FULL)), FULL);
    }

    @Logged
    public ExecutionDTO read(long id) {
        Execution entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new ExecutionDTO(entity, FLAT);
    }

    @Logged
    public ExecutionDTO readWithTestCaseExecutions(long id) {
        Execution entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new ExecutionDTO(entity, FULL);
    }

    @Logged
    public Page<ExecutionDTO> readAll(Pageable pageable) {
        notNull(pageable, new IllegalArgumentException("Pageable is null"));
        Page<Execution> page = getDefaultRepo().findAll(pageable);
        return page.map(e -> new ExecutionDTO(e, FLAT));
    }
}
