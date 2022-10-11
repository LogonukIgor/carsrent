package by.logonuk.controller.requests;

import lombok.Builder;
import lombok.Data;

@Data
public class UserCreateRequest {

    private String userName;

    private String surname;

    private String login;

    private String password;

    private String mail;
}
