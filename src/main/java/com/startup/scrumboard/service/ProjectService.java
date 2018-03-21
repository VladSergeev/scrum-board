package com.startup.scrumboard.service;

import com.startup.scrumboard.model.entity.Project;
import com.startup.scrumboard.model.enums.ProjectStatus;
import com.startup.scrumboard.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class ProjectService {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskBoardService taskBoardService;

    private final ProjectRepository projectRepository;


    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public Project create(Project project) {
        Assert.notNull(project, "Не указан проект!");
        project.setId(null);
        return projectRepository.save(project);

    }

    public Project update(Project project) {
        Assert.notNull(project, "Не указан проект!");
        Assert.notNull(project.getId(), "Не указан проект!");
        Project entity = projectRepository.findOne(project.getId());
        Assert.notNull(entity, "Проект не найден!");
        return projectRepository.save(project);
    }

    public Page<Project> list(Pageable pageable, String name) {
        Page<Project> projects;
        if (StringUtils.isEmpty(name)) {
            projects = projectRepository.findAll(pageable);
        } else {
            projects = projectRepository.findByNameLike(name, pageable);
        }
        return projects;
    }

    public void delete(String id) {
        Assert.notNull(id, "Не указан проект!");
        Project entity = projectRepository.findOne(id);
        Assert.notNull(entity, "Проект не найден!");
        projectRepository.delete(id);

    }

    public Project get(String id) {
        return projectRepository.findOne(id);
    }

}
