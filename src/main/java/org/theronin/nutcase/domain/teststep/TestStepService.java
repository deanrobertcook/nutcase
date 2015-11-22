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
    public TestStep create(TestStep testStep) {
        notNull(testStep, new IllegalArgumentException("TestStep is null"));
        isNull(testStep.getId(), new IllegalArgumentException("TestStep ID should be null"));
        validateEntity(testStep);
        return getDefaultRepo().save(testStep);
    }

    @Logged
    public void delete(TestStep testStep) {
        notNull(testStep, new IllegalArgumentException("TestStep is null"));
        notNull(testStep.getId(), new IllegalArgumentException("TestStep ID should not be null"));
        getDefaultRepo().delete(testStep);
    }

    @Logged
    public TestStep update(TestStep testStep) {
        notNull(testStep, new IllegalArgumentException("TestStep is null"));
        notNull(testStep.getId(), new IllegalArgumentException("TestStep ID should not be null"));
        validateEntity(testStep);
        return getDefaultRepo().save(testStep);
    }

    @Logged
    public TestStep read(Long id) {
        return getDefaultRepo().findOne(id);
    }
}
