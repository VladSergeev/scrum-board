package com.startup.scrumboard.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileDto {
    private String contentType;
    private String name;
    private long length;
    private byte[] content;
}
