package org.theronin.nutcase.domain.execution.teststepexecution;

import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theronin.nutcase.config.logging.Logged;
import static org.theronin.nutcase.domain.base.MapDept.FLAT;
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
        return new TestStepExecutionDTO(getDefaultRepo().save(new TestStepExecution(testStepExecution, FLAT)), FLAT);
    }

    @Logged
    public void delete(TestStepExecutionDTO testStepExecution) {
        notNull(testStepExecution, new IllegalArgumentException("TestStepExecution is null"));
        notNull(testStepExecution.getId(), new IllegalArgumentException("TestStepExecution ID should not be null"));
        getDefaultRepo().delete(new TestStepExecution(testStepExecution, FLAT));
    }

    @Logged
    public void delete(long id) {
        delete(read(id));
    }

    @Logged
    public TestStepExecutionDTO update(TestStepExecutionDTO testStepExecution) {
        notNull(testStepExecution, new IllegalArgumentException("TestStepExecution is null"));
        notNull(testStepExecution.getId(), new IllegalArgumentException("TestStepExecution ID should not be null"));
        return new TestStepExecutionDTO(getDefaultRepo().save(new TestStepExecution(testStepExecution, FLAT)), FLAT);
    }

    @Logged
    @Transactional(readOnly = true)
    public TestStepExecutionDTO read(long id) {
        TestStepExecution entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestStepExecutionDTO(entity, FLAT);
    }

    @Logged
    @Transactional(readOnly = true)
    public Page<TestStepExecutionDTO> readAll(Pageable pageable) {
        notNull(pageable, new IllegalArgumentException("Pageable is null"));
        Page<TestStepExecution> page = getDefaultRepo().findAll(pageable);
        return page.map(tse -> new TestStepExecutionDTO(tse, FLAT));
    }

}
