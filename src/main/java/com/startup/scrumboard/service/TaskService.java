package com.startup.scrumboard.service;

import com.startup.scrumboard.model.entity.Task;
import com.startup.scrumboard.model.entity.TaskBoard;
import com.startup.scrumboard.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    @Autowired
    private TaskBoardService taskBoardService;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public Task create(String boardId, Task task) {
        Assert.notNull(task, "Не указана задаача!");
        Assert.notNull(boardId, "Не указана доска для задачи!");
        Task entity = taskRepository.save(task);
        TaskBoard board = taskBoardService.get(boardId);
        board.getTasks().add(entity.getId());
        taskBoardService.update(board);
        return entity;

    }

    public Task update(Task task) {
        Assert.notNull(task, "Не указана задача!");
        Assert.notNull(task.getId(), "Не  указана задача!");
        Task entity = taskRepository.findOne(task.getId());
        Assert.notNull(entity, "Задача не найдена!");
        return taskRepository.save(task);
    }

    public Page<Task> list(Pageable pageable, String name) {
        Page<Task> tasks;
        if (StringUtils.isEmpty(name)) {
            tasks = taskRepository.findByNameLike(name, pageable);
        } else {
            tasks = taskRepository.findAll(pageable);
        }
        return tasks;
    }

    public void delete(String id) {
        Assert.notNull(id, "Не указана задаача!");
        Task entity = taskRepository.findOne(id);
        Assert.notNull(entity, "Задача не найдена!");
        taskRepository.delete(id);

    }

    public Task get(String id) {
        return taskRepository.findOne(id);
    }
}
