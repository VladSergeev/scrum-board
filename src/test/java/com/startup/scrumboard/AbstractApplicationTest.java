package com.startup.scrumboard;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.jayway.restassured.response.ValidatableResponseOptions;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {Application.class})
@TestPropertySource(locations="classpath:test-application.properties")
public abstract class AbstractApplicationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    ValidatableResponseOptions post(Object body, String url) {
        return given()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post(url)
                .then();
    }

    ValidatableResponseOptions get(Map params, String url) {
        return given()
                .params(params)
                .contentType(ContentType.JSON)
                .when()
                .get(url)
                .then();
    }
    ValidatableResponseOptions get( String url) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(url)
                .then();
    }

    ValidatableResponseOptions postFile(String inputName, byte[] bytes, String url) {
        return given().multiPart(inputName, "file.abc", bytes).post(url).then();
    }

    ValidatableResponseOptions delete( String url) {
        return given().delete(url).then();
    }

}
