package com.startup.scrumboard.model.entity;

import com.startup.scrumboard.model.enums.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 25.09.2016.
 */
@Getter
@Setter
public class Project extends BaseEntity {
    @NotNull(message = "Имя проекта не указано!")
    @Size(min = 5,message = "Имя проекта должно быть более 5 символов!")
    private String name;
    @NotNull(message = "Описание проекта не указано!")
    @Size(min = 1,message = "Описание проекта не должно быть пустым!")
    private String description;
    @NotNull(message = "Статус проекта не указан!")
    private ProjectStatus status;

    private String assign;
    private List<String> boards;

    public List<String> getBoards() {
        return boards == null ? new ArrayList<>() : boards;
    }

    private List<Comment> comments;
}
