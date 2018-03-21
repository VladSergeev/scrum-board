package com.startup.scrumboard.controller;

import com.startup.scrumboard.model.entity.User;
import com.startup.scrumboard.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.startup.scrumboard.Application.API;

@RestController
@RequestMapping(API + "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody @Valid  User user) {
        return ResponseEntity.ok(userService.update(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable String id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "login", dataType = "string", paramType = "query",
                    value = "login")
    })
    public ResponseEntity<Page<User>> getUsers(Pageable pageable, String login) throws Exception {

          return ResponseEntity.ok(userService.list(pageable, login));
    }
}
