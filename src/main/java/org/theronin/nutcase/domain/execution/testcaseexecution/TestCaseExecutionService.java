package org.theronin.nutcase.domain.execution.testcaseexecution;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theronin.nutcase.config.logging.Logged;
import static org.theronin.nutcase.domain.base.EntityValidator.validateEntity;
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
        TestCaseExecution entity = new TestCaseExecution(testCaseExecution, 2);
        validateEntity(entity);
        return new TestCaseExecutionDTO(getDefaultRepo().save(entity), 2);
    }

    @Logged
    public void delete(TestCaseExecutionDTO testCaseExecution) {
        notNull(testCaseExecution, new IllegalArgumentException("TestCaseExecution is null"));
        notNull(testCaseExecution.getId(), new IllegalArgumentException("TestCaseExecution ID should not be null"));
        TestCaseExecution entity = new TestCaseExecution(testCaseExecution, 1);
        getDefaultRepo().delete(entity);
    }

    @Logged
    public TestCaseExecutionDTO update(TestCaseExecutionDTO testCaseExecution) {
        notNull(testCaseExecution, new IllegalArgumentException("TestCaseExecution is null"));
        notNull(testCaseExecution.getId(), new IllegalArgumentException("TestCaseExecution ID should not be null"));
        TestCaseExecution entity = new TestCaseExecution(testCaseExecution, 2);
        validateEntity(entity);
        return new TestCaseExecutionDTO(getDefaultRepo().save(entity), 2);
    }

    @Logged
    public TestCaseExecutionDTO read(Long id) {
        notNull(id, new IllegalArgumentException("ID is null"));
        TestCaseExecution entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestCaseExecutionDTO(entity, 1);
    }

    @Logged
    public TestCaseExecutionDTO readWithTestStepExecutions(Long id) {
        notNull(id, new IllegalArgumentException("ID is null"));
        TestCaseExecution entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestCaseExecutionDTO(entity, 2);
    }
}
