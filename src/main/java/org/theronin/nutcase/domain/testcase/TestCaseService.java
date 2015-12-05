package org.theronin.nutcase.domain.testcase;

import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theronin.nutcase.config.logging.Logged;
import static org.theronin.nutcase.domain.base.MapDept.FLAT;
import static org.theronin.nutcase.domain.base.MapDept.FULL;
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
        return new TestCaseDTO(getDefaultRepo().save(new TestCase(testCase, FULL)), FULL);
    }

    @Logged
    public void delete(TestCaseDTO testCase) {
        notNull(testCase, new IllegalArgumentException("TestCase is null"));
        notNull(testCase.getId(), new IllegalArgumentException("TestCase ID should not be null"));
        getDefaultRepo().delete(new TestCase(testCase, FLAT));
    }

    @Logged
    public void delete(long id) {
        delete(read(id));
    }

    @Logged
    public TestCaseDTO update(TestCaseDTO testCase) {
        notNull(testCase, new IllegalArgumentException("TestCase is null"));
        notNull(testCase.getId(), new IllegalArgumentException("TestCase ID should not be null"));
        return new TestCaseDTO(getDefaultRepo().save(new TestCase(testCase, FULL)), FULL);
    }

    @Logged
    public TestCaseDTO read(long id) {
        TestCase entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestCaseDTO(entity, FLAT);
    }

    @Logged
    public TestCaseDTO readWithSteps(long id) {
        TestCase entity = getDefaultRepo().findOne(id);
        return entity == null ? null : new TestCaseDTO(entity, FULL);
    }

    @Logged
    public Page<TestCaseDTO> readAll(Pageable pageable) {
        notNull(pageable, new IllegalArgumentException("Pageable is null"));
        Page<TestCase> page = getDefaultRepo().findAll(pageable);
        return page.map(tc -> new TestCaseDTO(tc, FLAT));
    }
}
