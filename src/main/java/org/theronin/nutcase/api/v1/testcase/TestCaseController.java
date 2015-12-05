package org.theronin.nutcase.api.v1.testcase;

import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.theronin.nutcase.config.logging.Logged;
import org.theronin.nutcase.domain.testcase.TestCaseDTO;
import org.theronin.nutcase.domain.testcase.TestCaseService;

@RestController
@RequestMapping("/api/v1/testcases")
public class TestCaseController {

    private final TestCaseService testCaseService;

    @Inject
    TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @Logged
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity create(@RequestBody TestCaseDTO dto) {
        return new ResponseEntity<>(testCaseService.create(dto), HttpStatus.CREATED);
    }

    @Logged
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    HttpEntity read(@PathVariable long id) {
        return new ResponseEntity<>(testCaseService.read(id), HttpStatus.OK);
    }

    @Logged
    @RequestMapping(value = "", method = RequestMethod.GET)
    HttpEntity readAll(@PageableDefault Pageable pageable) {
        Page<TestCaseDTO> dtos = testCaseService.readAll(pageable);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Logged
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity update(@PathVariable long id, @RequestBody TestCaseDTO dto) {
        if (dto.getId() != id) {
            return new ResponseEntity<>("ID in URL does not match ID in body", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(testCaseService.update(dto), HttpStatus.OK);
    }

    @Logged
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    HttpEntity delete(@PathVariable long id) {
        testCaseService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    

}
