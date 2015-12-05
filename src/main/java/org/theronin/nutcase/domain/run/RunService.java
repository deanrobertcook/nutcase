package org.theronin.nutcase.domain.run;

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
        return new RunDTO(getDefaultRepo().save(new Run(run, FULL)), FULL);
    }

    @Logged
    public void delete(RunDTO run) {
        notNull(run, new IllegalArgumentException("Run is null"));
        notNull(run.getId(), new IllegalArgumentException("Run ID should not be null"));
        getDefaultRepo().delete(new Run(run, FLAT));
    }

    @Logged
    public void delete(long id) {
        delete(read(id));
    }

    @Logged
    public RunDTO update(RunDTO run) {
        notNull(run, new IllegalArgumentException("Run is null"));
        notNull(run.getId(), new IllegalArgumentException("Run ID should not be null"));
        return new RunDTO(getDefaultRepo().save(new Run(run, FULL)), FULL);
    }

    @Logged
    public RunDTO read(long id) {
        Run entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new RunDTO(entity, FLAT);
    }

    @Logged
    public RunDTO readWithTestcasesAndExecutions(long id) {
        Run entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new RunDTO(entity, FULL);
    }

    @Logged
    public Page<RunDTO> readAll(Pageable pageable) {
        notNull(pageable, new IllegalArgumentException("Pageable is null"));
        Page<Run> page = getDefaultRepo().findAll(pageable);
        return page.map(r -> new RunDTO(r, FLAT));
    }
}
