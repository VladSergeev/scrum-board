package com.startup.scrumboard.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.startup.scrumboard.model.dto.FileDto;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final GridFsTemplate gridFsTemplate;
    private final UserService userService;

    public FileService(GridFsTemplate gridFsTemplate, UserService userService) {
        this.gridFsTemplate = gridFsTemplate;
        this.userService = userService;
    }

    public FileDto findById(String id) throws IOException {
        GridFSDBFile fsFile = gridFsTemplate.findOne(Query.query(GridFsCriteria.where("_id").is(id)));
        if (fsFile == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        fsFile.writeTo(os);

        FileDto fileDto = new FileDto();
        fileDto.setContent(os.toByteArray());
        fileDto.setContentType(fsFile.getContentType());
        fileDto.setLength(fsFile.getLength());
        fileDto.setName(fsFile.getFilename());
        return fileDto;
    }

    public List<String> findAll(String user) {
        List<GridFSDBFile> list = gridFsTemplate.find(Query.query(GridFsCriteria.whereMetaData("user").is(user)));
        return list.stream().map(GridFSFile::getId).map(Object::toString).collect(Collectors.toList());
    }

    public void deleteFile(String id) {
        gridFsTemplate.delete(Query.query(GridFsCriteria.where("_id").is(id)));
    }

    public String save(MultipartFile file) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("user", userService.getCurrentUser());
        GridFSFile fsFile = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metaData);
        return fsFile.getId().toString();
    }
}
