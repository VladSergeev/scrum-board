package com.startup.scrumboard;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;


public class FileControllerTest extends AbstractApplicationTest {

    @Test
    public void mustCreateFile() throws Exception {
        byte[] bytes = {1, 2, 3};
        postFile("file", bytes, "/api/v1/files").statusCode(HttpStatus.SC_OK);
    }


    @Test
    public void mustRemoveFile() throws Exception {
        byte[] bytes = {1, 2, 3};
        String id = postFile("file", bytes, "/api/v1/files").statusCode(HttpStatus.SC_OK).extract().body().asString();
        get("/api/v1/files/" + id).statusCode(HttpStatus.SC_OK);
        delete("/api/v1/files/" + id).statusCode(HttpStatus.SC_NO_CONTENT);
        get("/api/v1/files/" + id).statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void mustGetFile() throws Exception {
        byte[] actual = {1, 2, 3};
        String id = postFile("file", actual, "/api/v1/files").statusCode(HttpStatus.SC_OK).extract().body().asString();
        byte[] expected = get("/api/v1/files/" + id).statusCode(HttpStatus.SC_OK).extract().body().asByteArray();
        Assert.assertArrayEquals(actual, expected);
    }

    @Test
    public void mustGetAllIdFilesByUser() {
        byte[] bytes = {1, 2, 3};
        String expected = postFile("file", bytes, "/api/v1/files").statusCode(HttpStatus.SC_OK).extract().body().asString();
        String[] actual = get("/api/v1/files/").statusCode(HttpStatus.SC_OK).extract().body().as(String[].class);
        Stream.of(actual).filter(expected::equals).findAny().orElseThrow(RuntimeException::new);
    }


}
