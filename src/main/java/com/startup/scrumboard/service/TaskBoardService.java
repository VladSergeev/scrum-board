package com.startup.scrumboard.service;

import com.startup.scrumboard.model.entity.Project;
import com.startup.scrumboard.model.entity.TaskBoard;
import com.startup.scrumboard.repository.TaskBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class TaskBoardService {
    @Autowired
    private ProjectService projectService;
    private final TaskBoardRepository taskBoardRepository;

    public TaskBoardService(TaskBoardRepository taskBoardRepository) {
        this.taskBoardRepository = taskBoardRepository;
    }


    public TaskBoard create(String projectId, TaskBoard task) {
        Assert.notNull(task, "Не указана задаача!");
        Assert.notNull(projectId, "Не указан проект для доски!");
        TaskBoard board = taskBoardRepository.save(task);
        Project project = projectService.get(projectId);
        project.getBoards().add(board.getId());
        projectService.update(project);
        return board;

    }

    public TaskBoard update(TaskBoard task) {
        Assert.notNull(task, "Не указана задача!");
        Assert.notNull(task.getId(), "Не  указана задача!");
        TaskBoard entity = taskBoardRepository.findOne(task.getId());
        Assert.notNull(entity, "Задача не найдена!");
        return taskBoardRepository.save(task);
    }

    public Page<TaskBoard> list(Pageable pageable, String name) {
        Page<TaskBoard> taskBoards;
        if (StringUtils.isEmpty(name)) {
            taskBoards = taskBoardRepository.findByNameLike(name, pageable);
        } else {
            taskBoards = taskBoardRepository.findAll(pageable);
        }
        return taskBoards;
    }

    public void delete(String id) {
        Assert.notNull(id, "Не указана задаача!");
        TaskBoard entity = taskBoardRepository.findOne(id);
        Assert.notNull(entity, "Задача не найдена!");
        taskBoardRepository.delete(id);

    }

    public TaskBoard get(String id) {
        return taskBoardRepository.findOne(id);
    }
}
