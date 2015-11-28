package org.theronin.nutcase.domain.testcase;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theronin.nutcase.config.logging.Logged;
import static org.theronin.nutcase.domain.base.EntityValidator.validateEntity;
import static org.theronin.nutcase.domain.base.ParameterValidator.isNull;
import static org.theronin.nutcase.domain.base.ParameterValidator.notNull;

@Service
@Transactional
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;

    @Inject
    public TestCaseService(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    private TestCaseRepository getDefaultRepo() {
        return testCaseRepository;
    }

    @Logged
    public TestCaseDTO create(TestCaseDTO testCase) {
        notNull(testCase, new IllegalArgumentException("TestCase is null"));
        isNull(testCase.getId(), new IllegalArgumentException("TestCase ID should be null"));
        TestCase entity = new TestCase(testCase, 2);
        validateEntity(entity);
        return new TestCaseDTO(getDefaultRepo().save(entity), 2);
    }

    @Logged
    public void delete(TestCaseDTO testCase) {
        notNull(testCase, new IllegalArgumentException("TestCase is null"));
        notNull(testCase.getId(), new IllegalArgumentException("TestCase ID should not be null"));
        TestCase entity = new TestCase(testCase, 1);
        validateEntity(entity);
        getDefaultRepo().delete(entity);
    }

    @Logged
    public TestCaseDTO update(TestCaseDTO testCase) {
        notNull(testCase, new IllegalArgumentException("TestCase is null"));
        notNull(testCase.getId(), new IllegalArgumentException("TestCase ID should not be null"));
        TestCase entity = new TestCase(testCase, 2);
        validateEntity(entity);
        return new TestCaseDTO(getDefaultRepo().save(entity), 2);
    }

    @Logged
    public TestCaseDTO read(Long id) {
        notNull(id, new IllegalArgumentException("ID is null"));
        TestCase entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestCaseDTO(entity, 1);
    }

    @Logged
    public TestCaseDTO readWithSteps(Long id) {
        notNull(id, new IllegalArgumentException("ID is null"));
        TestCase entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestCaseDTO(entity, 2);
    }
}
