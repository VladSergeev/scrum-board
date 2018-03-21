package com.startup.scrumboard.controller;

import com.startup.scrumboard.model.entity.Project;
import com.startup.scrumboard.model.entity.TaskBoard;
import com.startup.scrumboard.service.TaskBoardService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.startup.scrumboard.Application.API;

/**
 * Created by vsergeev on 28.12.2016.
 */
@RestController
@RequestMapping(API + "/board")
public class TaskBoardController {
    private final TaskBoardService taskBoardService;

    public TaskBoardController(TaskBoardService taskBoardService) {
        this.taskBoardService = taskBoardService;
    }

    @PostMapping("/create/{projectId}")
    public ResponseEntity<TaskBoard> create(@PathVariable String projectId, @RequestBody TaskBoard project) {
        return ResponseEntity.ok(taskBoardService.create(projectId, project));
    }

    @PostMapping("/update")
    public ResponseEntity<TaskBoard> update(@RequestBody TaskBoard project) {
        return ResponseEntity.ok(taskBoardService.update(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        taskBoardService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskBoard> get(@PathVariable String id) {
        return ResponseEntity.ok(taskBoardService.get(id));
    }

    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "query",
                    value = "name")
    })
    public ResponseEntity<Page<TaskBoard>> getUsers(Pageable pageable, String name) {
        return ResponseEntity.ok(taskBoardService.list(pageable, name));
    }
}
