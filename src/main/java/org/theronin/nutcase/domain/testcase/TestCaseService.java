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
    public TestCase create(TestCase testCase) {
        notNull(testCase, new IllegalArgumentException("TestCase is null"));
        isNull(testCase.getId(), new IllegalArgumentException("TestCase ID should be null"));
        validateEntity(testCase);
        return getDefaultRepo().save(testCase);
    }

    @Logged
    public void delete(TestCase testCase) {
        notNull(testCase, new IllegalArgumentException("TestCase is null"));
        notNull(testCase.getId(), new IllegalArgumentException("TestCase ID should not be null"));
        getDefaultRepo().delete(testCase);
    }

    @Logged
    public TestCase update(TestCase testCase) {
        notNull(testCase, new IllegalArgumentException("TestCase is null"));
        notNull(testCase.getId(), new IllegalArgumentException("TestCase ID should not be null"));
        validateEntity(testCase);
        return getDefaultRepo().save(testCase);
    }

    @Logged
    public TestCase read(Long id) {
        return getDefaultRepo().findOne(id);
    }
}
