package com.startup.scrumboard.model.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Getter @Setter
public class BaseEntity {
    @Id
    private String id;
}
