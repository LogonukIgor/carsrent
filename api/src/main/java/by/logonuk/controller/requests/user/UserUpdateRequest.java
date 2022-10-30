package by.logonuk.controller.requests.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "Request for user update")
public class UserUpdateRequest extends UserCreateRequest{

    @NotNull(message = "User id must not be null")
    @Pattern(regexp = "^\\d*$", message = "User id must contain only numbers")
    @Schema(description = "User id", required = true, type = "string")
    private String userId;
}
