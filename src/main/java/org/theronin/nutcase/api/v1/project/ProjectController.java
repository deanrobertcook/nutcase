package org.theronin.nutcase.api.v1.project;

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
import org.theronin.nutcase.domain.project.ProjectDTO;
import org.theronin.nutcase.domain.project.ProjectService;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Inject
    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Logged
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity create(@RequestBody ProjectDTO dto) {
        return new ResponseEntity<>(projectService.create(dto), HttpStatus.CREATED);
    }

    @Logged
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    HttpEntity read(@PathVariable long id) {
        return new ResponseEntity<>(projectService.read(id), HttpStatus.OK);
    }

    @Logged
    @RequestMapping(value = "", method = RequestMethod.GET)
    HttpEntity readAll(@PageableDefault Pageable pageable) {
        Page<ProjectDTO> dtos = projectService.readAll(pageable);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Logged
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity update(@PathVariable long id, @RequestBody ProjectDTO dto) {
        if (dto.getId() != id) {
            return new ResponseEntity<>("ID in URL does not match ID in body", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(projectService.update(dto), HttpStatus.OK);
    }

    @Logged
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    HttpEntity delete(@PathVariable long id) {
        projectService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    

}
