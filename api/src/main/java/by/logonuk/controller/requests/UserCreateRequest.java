package by.logonuk.controller.requests;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserCreateRequest {

    private String userName;

    private String surname;

    private String login;

    private String password;

    private String mail;
}
