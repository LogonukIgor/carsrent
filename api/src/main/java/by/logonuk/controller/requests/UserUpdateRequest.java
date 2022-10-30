package by.logonuk.controller.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserUpdateRequest extends UserCreateRequest{

    @NotNull(message = "User id must not be null")
    @Pattern(regexp = "^\\d*$", message = "User id must contain only numbers")
    private String userId;
}
