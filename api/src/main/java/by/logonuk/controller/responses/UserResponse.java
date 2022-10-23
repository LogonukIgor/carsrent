package by.logonuk.controller.responses;

import by.logonuk.domain.Deal;
import by.logonuk.domain.DrivingLicence;
import by.logonuk.domain.Role;
import by.logonuk.domain.embed.user.Credentials;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {

    private Long id;

    private String userName;

    private String surname;

    private Credentials credentials;

    private Set<Role> roles;

    private DrivingLicence drivingLicence;

    private Deal deal;
}
