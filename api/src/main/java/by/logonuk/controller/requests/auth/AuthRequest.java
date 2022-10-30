package by.logonuk.controller.requests.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login request")
public class AuthRequest {

    @Schema(description = "User login", required = true, type = "string")
    private String login;

    @Schema(description = "User password", required = true, type = "string")
    private String password;
}
