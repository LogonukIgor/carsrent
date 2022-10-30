package by.logonuk.controller.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserCreateRequest {

    @NotNull(message = "User name must not be null")
    @Size(min = 2, max = 20, message = "User name size must be between 2 and 20")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "User name must start with a capital letter and contain only letters")
    private String userName;

    @NotNull(message = "User surname must not be null")
    @Size(min = 2, max = 20, message = "Surname size must be between 2 and 20")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Surname must start with a capital letter and contain only letters")
    private String surname;

    @NotNull(message = "User login must not be null")
    @Size(min = 2, max = 100, message = "Login size must be between 2 and 100")
    private String login;

    @NotNull(message = "User password must not be null")
    @Size(min = 4, max = 200, message = "Password size must be between 2 and 200")
    private String password;

    @NotNull(message = "User mail must not be null")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Invalid mail format")
    @Size(min = 2, max = 100, message = "Mail size must be between 2 and 100")
    private String mail;
}
