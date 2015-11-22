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
    public TestCaseExecution create(TestCaseExecution testCaseExecution) {
        notNull(testCaseExecution, new IllegalArgumentException("TestCaseExecution is null"));
        isNull(testCaseExecution.getId(), new IllegalArgumentException("TestCaseExecution ID should be null"));
        validateEntity(testCaseExecution);
        return getDefaultRepo().save(testCaseExecution);
    }

    @Logged
    public void delete(TestCaseExecution testCaseExecution) {
        notNull(testCaseExecution, new IllegalArgumentException("TestCaseExecution is null"));
        notNull(testCaseExecution.getId(), new IllegalArgumentException("TestCaseExecution ID should not be null"));
        getDefaultRepo().delete(testCaseExecution);
    }

    @Logged
    public TestCaseExecution update(TestCaseExecution testCaseExecution) {
        notNull(testCaseExecution, new IllegalArgumentException("TestCaseExecution is null"));
        notNull(testCaseExecution.getId(), new IllegalArgumentException("TestCaseExecution ID should not be null"));
        validateEntity(testCaseExecution);
        return getDefaultRepo().save(testCaseExecution);
    }

    @Logged
    public TestCaseExecution read(Long id) {
        return getDefaultRepo().findOne(id);
    }
}
