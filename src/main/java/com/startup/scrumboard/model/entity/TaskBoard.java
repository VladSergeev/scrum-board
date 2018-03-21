package com.startup.scrumboard.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 11.09.2016.
 */
@Getter
@Setter
public class TaskBoard extends BaseEntity {
    @NotNull(message = "Имя доски не указано!")
    @Size(min = 5, message = "Имя доски должно быть более 5 символов!")
    private String name;

    @NotNull(message = "Описание доски не указано!")
    @Size(min = 5, message = "Описание доски должно быть более 5 символов!")
    private String description;

    private List<String> tasks;
    private Date dateFrom;
    private Date dateTo;
}
