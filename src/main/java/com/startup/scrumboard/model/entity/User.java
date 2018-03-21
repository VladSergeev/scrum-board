package com.startup.scrumboard.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Created by User on 11.09.2016.
 */
@Getter
@Setter
public class User {
    @Id
    @NotNull(message = "Login не указан!")
    @Size(min = 1, message = "Login должен быть не пустым!")
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotEmpty(message = "E-mail не может быть пустым.")
    @NotNull(message = "E-mail не указан.")
    @Email( message = "E-mail невалиден!")
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
}
