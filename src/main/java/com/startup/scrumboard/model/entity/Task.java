package com.startup.scrumboard.model.entity;

import com.startup.scrumboard.model.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Task extends BaseEntity {
    @NotNull(message = "Имя задачи не указано!")
    @Size(min = 5, message = "Имя задачи должно быть более 5 символов!")
    private String name;
    @NotNull(message = "Тип задачи не указан!")
    private String type;
    @NotNull(message = "Статус задачи не указан!")
    private TaskStatus status;
    @NotNull(message = "Описание задачи не указано!")
    @Size(min = 5, message = "Описание задачи должно быть более 5 символов!")
    private String description;
    private String assign;
    private Date dueTime;
    private Long periodTime;
    private List<String> subTasks;
    private List<Comment> comments;
    private Date createTime;
    private Date updateTime;

}
