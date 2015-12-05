package org.theronin.nutcase.domain.execution.testcaseexecution;

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
public class TestCaseExecutionService {

    private final TestCaseExecutionRepository testCaseExecutionRepository;

    @Inject
    public TestCaseExecutionService(TestCaseExecutionRepository testCaseExecutionRepository) {
        this.testCaseExecutionRepository = testCaseExecutionRepository;
    }

    private TestCaseExecutionRepository getDefaultRepo() {
        return testCaseExecutionRepository;
    }

    @Logged
    public TestCaseExecutionDTO create(TestCaseExecutionDTO testCaseExecution) {
        notNull(testCaseExecution, new IllegalArgumentException("TestCaseExecution is null"));
        isNull(testCaseExecution.getId(), new IllegalArgumentException("TestCaseExecution ID should be null"));
        return new TestCaseExecutionDTO(getDefaultRepo().save(new TestCaseExecution(testCaseExecution, FULL)), FULL);
    }

    @Logged
    public void delete(TestCaseExecutionDTO testCaseExecution) {
        notNull(testCaseExecution, new IllegalArgumentException("TestCaseExecution is null"));
        notNull(testCaseExecution.getId(), new IllegalArgumentException("TestCaseExecution ID should not be null"));
        getDefaultRepo().delete(new TestCaseExecution(testCaseExecution, FLAT));
    }

    @Logged
    public void delete(long id) {
        delete(read(id));
    }

    @Logged
    public TestCaseExecutionDTO update(TestCaseExecutionDTO testCaseExecution) {
        notNull(testCaseExecution, new IllegalArgumentException("TestCaseExecution is null"));
        notNull(testCaseExecution.getId(), new IllegalArgumentException("TestCaseExecution ID should not be null"));
        return new TestCaseExecutionDTO(getDefaultRepo().save(new TestCaseExecution(testCaseExecution, FULL)), FULL);
    }

    @Logged
    public TestCaseExecutionDTO read(long id) {
        TestCaseExecution entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestCaseExecutionDTO(entity, FLAT);
    }

    @Logged
    public TestCaseExecutionDTO readWithTestStepExecutions(long id) {
        TestCaseExecution entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestCaseExecutionDTO(entity, FULL);
    }

    @Logged
    public Page<TestCaseExecutionDTO> readAll(Pageable pageable) {
        notNull(pageable, new IllegalArgumentException("Pageable is null"));
        Page<TestCaseExecution> page = getDefaultRepo().findAll(pageable);
        return page.map(tce -> new TestCaseExecutionDTO(tce, FLAT));
    }
}
