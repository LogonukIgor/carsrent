package by.logonuk.controller.requests.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Request for user create")
public class UserCreateRequest {

    @NotNull(message = "User name must not be null")
    @Size(min = 2, max = 20, message = "User name size must be between 2 and 20")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "User name must start with a capital letter and contain only letters")
    @Schema(description = "User name", required = true, type = "string")
    private String userName;

    @NotNull(message = "User surname must not be null")
    @Size(min = 2, max = 20, message = "Surname size must be between 2 and 20")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Surname must start with a capital letter and contain only letters")
    @Schema(description = "User surname", required = true, type = "string")
    private String surname;

    @NotNull(message = "User login must not be null")
    @Size(min = 2, max = 100, message = "Login size must be between 2 and 100")
    @Schema(description = "User login", required = true, type = "string")
    private String login;

    @NotNull(message = "User password must not be null")
    @Size(min = 4, max = 200, message = "Password size must be between 2 and 200")
    @Schema(description = "User password", required = true, type = "string")
    private String password;

    @NotNull(message = "User mail must not be null")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Invalid mail format")
    @Size(min = 2, max = 100, message = "Mail size must be between 2 and 100")
    @Schema(description = "User mail address", required = true, type = "string")
    private String mail;
}
