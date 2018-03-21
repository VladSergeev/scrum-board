package com.startup.scrumboard.controller;

import com.startup.scrumboard.model.entity.Task;
import com.startup.scrumboard.model.entity.TaskBoard;
import com.startup.scrumboard.service.TaskBoardService;
import com.startup.scrumboard.service.TaskService;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(API + "/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create/{boardId}")
    public ResponseEntity<Task> create(@PathVariable String boardId, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.create(boardId, task));
    }


}
