package by.logonuk.controller.responses.user;

import by.logonuk.controller.responses.deal.DealResponse;
import by.logonuk.controller.responses.licence.DrivingLicenceResponse;
import by.logonuk.domain.Role;
import by.logonuk.domain.attachments.Credentials;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {

    private Long id;

    private String userName;

    private String surname;

    private Credentials credentials;

    private Set<Role> roles;

    private DrivingLicenceResponse drivingLicence;

    private DealResponse deal;
}
