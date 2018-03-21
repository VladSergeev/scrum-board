package com.startup.scrumboard.controller;

import com.startup.scrumboard.model.dto.FileDto;
import com.startup.scrumboard.service.FileService;
import com.startup.scrumboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.startup.scrumboard.Application.API;

@Controller
@RequestMapping(API + "/files")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity save(@RequestParam("file") MultipartFile file) {
        try {
            String id = fileService.save(file);
            return ResponseEntity.ok(id);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/{id}")
    public HttpEntity<byte[]> get(@PathVariable("id") String id) {
        try {
            FileDto file = fileService.findById(id);

            if (file == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-disposition", "inline;filename=" + file.getName());
            ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
            builder.headers(headers);
            builder.contentLength(file.getLength());
            builder.contentType(MediaType.parseMediaType(file.getContentType()));
            return builder.body(file.getContent());
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{id}")
    public HttpEntity<byte[]> delete(@PathVariable("id") String id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public HttpEntity<List<String>> getAllFiles() {
        List<String> ids = fileService.findAll(userService.getCurrentUser());
        return ResponseEntity.ok(ids);
    }

}