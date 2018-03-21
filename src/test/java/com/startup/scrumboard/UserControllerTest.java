package com.startup.scrumboard;

import com.startup.scrumboard.model.entity.User;
import com.startup.scrumboard.repository.UserRepository;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasItem;


public class UserControllerTest extends AbstractApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void mustSaveUser() throws Exception {
        User test = userRepository.findByLogin("test");
        Assert.assertNull(test);

        User o = new User();
        o.setLogin("test");
        o.setPassword("testtest");
        post(o, "/api/v1/user/create").statusCode(HttpStatus.SC_OK);
        test = userRepository.findByLogin("test");
        Assert.assertNotNull(test);
    //    userRepository.delete(test.getId());
    }

    @Test
    public void mustReturnList() throws Exception {
        User o = new User();
        o.setLogin("test");
        o.setPassword("testtest");
        userRepository.save(o);

        HashMap<String, String> map = new HashMap<>();
        map.put("page","0");
        map.put("size","10");
        map.put("login","test");

        get(map, "/api/v1/user/list")
                .body("content.login",hasItem("test"))
                .log().body()
                .statusCode(HttpStatus.SC_OK);
    }


}
