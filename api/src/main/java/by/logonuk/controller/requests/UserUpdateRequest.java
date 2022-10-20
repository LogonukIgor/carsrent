package by.logonuk.controller.requests;

import lombok.Data;

@Data
public class UserUpdateRequest extends UserCreateRequest{

    private String userId;
}
