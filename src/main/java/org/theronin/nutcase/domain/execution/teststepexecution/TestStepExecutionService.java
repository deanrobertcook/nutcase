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
    public TestStepExecutionDTO create(TestStepExecutionDTO testStepExecution) {
        notNull(testStepExecution, new IllegalArgumentException("TestStepExecution is null"));
        isNull(testStepExecution.getId(), new IllegalArgumentException("TestStepExecution ID should be null"));
        TestStepExecution entity = new TestStepExecution(testStepExecution, 1);
        validateEntity(entity);
        return new TestStepExecutionDTO(getDefaultRepo().save(entity), 1);
    }

    @Logged
    public void delete(TestStepExecutionDTO testStepExecution) {
        notNull(testStepExecution, new IllegalArgumentException("TestStepExecution is null"));
        notNull(testStepExecution.getId(), new IllegalArgumentException("TestStepExecution ID should not be null"));
        TestStepExecution entity = new TestStepExecution(testStepExecution, 1);
        getDefaultRepo().delete(entity);
    }

    @Logged
    public TestStepExecutionDTO update(TestStepExecutionDTO testStepExecution) {
        notNull(testStepExecution, new IllegalArgumentException("TestStepExecution is null"));
        notNull(testStepExecution.getId(), new IllegalArgumentException("TestStepExecution ID should not be null"));
        TestStepExecution entity = new TestStepExecution(testStepExecution, 1);
        validateEntity(entity);
        return new TestStepExecutionDTO(getDefaultRepo().save(entity), 1);
    }

    @Logged
    public TestStepExecutionDTO read(Long id) {
        TestStepExecution entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestStepExecutionDTO(entity, 1);
    }

}
