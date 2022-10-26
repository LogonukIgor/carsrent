package by.logonuk.controller.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class DrivingLicenceUpdateRequest extends DrivingLicenceCreateRequest{

    @NotNull(message = "Licence id must not be null")
    @Pattern(regexp = "^\\d*", message = "Licence id must contain only numbers")
    private String licenceId;
}
