package com.startup.scrumboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static final String API = "/api/v1";
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("http://localhost:8080");
        System.out.println("http://localhost:8080/swagger-ui.html");
    }
}

//почта=)
//startup.scrumboard@gmail.com
//b37d0bb73a2f688ecaee01647f41e3e5