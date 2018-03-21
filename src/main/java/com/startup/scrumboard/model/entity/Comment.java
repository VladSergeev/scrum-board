package com.startup.scrumboard.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by User on 11.09.2016.
 */
@Getter @Setter
public class Comment extends BaseEntity {
    @NotNull(message = "Комментарий не указан!")
    @Size(min = 1,message = "Комментарий не должен быть пустым!")
    private String description;
    @NotNull(message = "Автор комментария не указан!")
    private String user;
}
