package by.logonuk.controller.responses;

import by.logonuk.domain.Role;
import by.logonuk.domain.embed.user.Credentials;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Set;

@Data
@ApiModel(description = "user response")
public class UserResponse {

    private Long id;

    private String userName;

    private String surname;

    private Credentials credentials;

    private Set<Role> roles;
}
