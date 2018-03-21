package com.startup.scrumboard.controller;

import com.startup.scrumboard.model.entity.Project;
import com.startup.scrumboard.model.enums.ProjectStatus;
import com.startup.scrumboard.service.ProjectService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.startup.scrumboard.Application.API;

/**
 * Created by vsergeev on 28.12.2016.
 */
@RestController
@RequestMapping(value = API + "/project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.create(project));
    }

    @PutMapping
    public ResponseEntity<Project> update(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.update(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> get(@PathVariable String id) {
        return ResponseEntity.ok(projectService.get(id));
    }

    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "query",
                    value = "name")
    })
    public ResponseEntity<Page<Project>> getUsers(Pageable pageable, String name) {
        return ResponseEntity.ok(projectService.list(pageable,name));
    }
}
