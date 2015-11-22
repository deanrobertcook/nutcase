package org.theronin.nutcase.domain.execution.teststepexecution;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theronin.nutcase.config.logging.Logged;
import static org.theronin.nutcase.domain.base.EntityValidator.validateEntity;
import static org.theronin.nutcase.domain.base.ParameterValidator.isNull;
import static org.theronin.nutcase.domain.base.ParameterValidator.notNull;

@Service
@Transactional
public class TestStepExecutionService {

    private final TestStepExecutionRepository testStepExecutionRepository;

    @Inject
    public TestStepExecutionService(TestStepExecutionRepository testStepExecutionRepository) {
        this.testStepExecutionRepository = testStepExecutionRepository;
    }

    private TestStepExecutionRepository getDefaultRepo() {
        return testStepExecutionRepository;
    }

    @Logged
    public TestStepExecution create(TestStepExecution testStepExecution) {
        notNull(testStepExecution, new IllegalArgumentException("TestStepExecution is null"));
        isNull(testStepExecution.getId(), new IllegalArgumentException("TestStepExecution ID should be null"));
        validateEntity(testStepExecution);
        return getDefaultRepo().save(testStepExecution);
    }

    @Logged
    public void delete(TestStepExecution testStepExecution) {
        notNull(testStepExecution, new IllegalArgumentException("TestStepExecution is null"));
        notNull(testStepExecution.getId(), new IllegalArgumentException("TestStepExecution ID should not be null"));
        getDefaultRepo().delete(testStepExecution);
    }

    @Logged
    public TestStepExecution update(TestStepExecution testStepExecution) {
        notNull(testStepExecution, new IllegalArgumentException("TestStepExecution is null"));
        notNull(testStepExecution.getId(), new IllegalArgumentException("TestStepExecution ID should not be null"));
        validateEntity(testStepExecution);
        return getDefaultRepo().save(testStepExecution);
    }

    @Logged
    public TestStepExecution read(Long id) {
        return getDefaultRepo().findOne(id);
    }
}
