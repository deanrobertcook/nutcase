package org.theronin.nutcase.api.v1.testcase;

import javax.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import org.springframework.boot.test.SpringApplicationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.theronin.nutcase.Application;
import org.theronin.nutcase.domain.project.ProjectRepository;
import org.theronin.nutcase.domain.run.RunRepository;
import org.theronin.nutcase.domain.testcase.TestCaseDTO;
import org.theronin.nutcase.domain.testcase.TestCaseRepository;
import org.theronin.nutcase.domain.testcase.TestCaseService;
import org.theronin.nutcase.domain.teststep.TestStepRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestCaseControllerTest {

    private static final Logger log = LoggerFactory.getLogger(TestCaseControllerTest.class);

    @Rule
    public TestName name = new TestName();

    private MockMvc mockMvc;

    @Inject
    private WebApplicationContext webApplicationContext;

    @Inject
    private TestCaseService testCaseService;

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private RunRepository runRepository;

    @Inject
    private TestCaseRepository testCaseRepository;

    @Inject
    private TestStepRepository testStepRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        projectRepository.deleteAllInBatch();
        testCaseRepository.deleteAllInBatch();
        testStepRepository.deleteAllInBatch();
        runRepository.deleteAllInBatch();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testReadAll() throws Exception {
        log.info(name.getMethodName());

        TestCaseDTO dto = new TestCaseDTO();
        dto.setTestId(12L);
        dto.setDescription("desc1");
        testCaseService.create(dto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/testcases"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
    }

    @Test
    public void testReadAllSorted() throws Exception {
        log.info(name.getMethodName());

        TestCaseDTO dto = new TestCaseDTO();
        dto.setTestId(12L);
        dto.setDescription("desc1");
        testCaseService.create(dto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/testcases?page=0&size=7&sort=testId"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
    }

}
