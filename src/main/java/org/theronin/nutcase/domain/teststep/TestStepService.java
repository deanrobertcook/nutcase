package org.theronin.nutcase.domain.teststep;

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
    public TestStepDTO create(TestStepDTO testStep) {
        notNull(testStep, new IllegalArgumentException("TestStep is null"));
        isNull(testStep.getId(), new IllegalArgumentException("TestStep ID should be null"));
        return new TestStepDTO(getDefaultRepo().save(new TestStep(testStep, FLAT)), FLAT);
    }

    @Logged
    public void delete(TestStepDTO testStep) {
        notNull(testStep, new IllegalArgumentException("TestStep is null"));
        notNull(testStep.getId(), new IllegalArgumentException("TestStep ID should not be null"));
        getDefaultRepo().delete(new TestStep(testStep, FLAT));
    }

    @Logged
    public void delete(long id) {
        delete(read(id));
    }

    @Logged
    public TestStepDTO update(TestStepDTO testStep) {
        notNull(testStep, new IllegalArgumentException("TestStep is null"));
        notNull(testStep.getId(), new IllegalArgumentException("TestStep ID should not be null"));
        return new TestStepDTO(getDefaultRepo().save(new TestStep(testStep, FLAT)), FLAT);
    }

    @Logged
    public TestStepDTO read(long id) {
        TestStep entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestStepDTO(entity, FLAT);
    }

    @Logged
    public Page<TestStepDTO> readAll(Pageable pageable) {
        notNull(pageable, new IllegalArgumentException("Pageable is null"));
        Page<TestStep> page = getDefaultRepo().findAll(pageable);
        return page.map(ts -> new TestStepDTO(ts, FLAT));
    }
}
