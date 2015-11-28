package org.theronin.nutcase.domain.teststep;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theronin.nutcase.config.logging.Logged;
import static org.theronin.nutcase.domain.base.EntityValidator.validateEntity;
import static org.theronin.nutcase.domain.base.ParameterValidator.isNull;
import static org.theronin.nutcase.domain.base.ParameterValidator.notNull;
import org.theronin.nutcase.domain.execution.Execution;

@Service
@Transactional
public class TestStepService {

    private final TestStepRepository testStepRepository;

    @Inject
    public TestStepService(TestStepRepository testStepRepository) {
        this.testStepRepository = testStepRepository;
    }

    private TestStepRepository getDefaultRepo() {
        return testStepRepository;
    }

    @Logged
    public Execution createExecutionFromProject() {

        return null;
    }

    @Logged
    public TestStepDTO create(TestStepDTO testStep) {
        notNull(testStep, new IllegalArgumentException("TestStep is null"));
        isNull(testStep.getId(), new IllegalArgumentException("TestStep ID should be null"));
        TestStep entity = new TestStep(testStep, 1);
        validateEntity(entity);
        return new TestStepDTO(getDefaultRepo().save(entity), 1);
    }

    @Logged
    public void delete(TestStepDTO testStep) {
        notNull(testStep, new IllegalArgumentException("TestStep is null"));
        notNull(testStep.getId(), new IllegalArgumentException("TestStep ID should not be null"));
        TestStep entity = new TestStep(testStep, 1);
        getDefaultRepo().delete(entity);
    }

    @Logged
    public TestStepDTO update(TestStepDTO testStep) {
        notNull(testStep, new IllegalArgumentException("TestStep is null"));
        notNull(testStep.getId(), new IllegalArgumentException("TestStep ID should not be null"));
        TestStep entity = new TestStep(testStep, 1);
        validateEntity(entity);
        return new TestStepDTO(getDefaultRepo().save(entity), 1);
    }

    @Logged
    public TestStepDTO read(Long id) {
        TestStep entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestStepDTO(entity, 1);
    }
}
