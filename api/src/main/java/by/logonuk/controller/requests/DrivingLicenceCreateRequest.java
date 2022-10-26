package by.logonuk.controller.requests;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
public class DrivingLicenceCreateRequest {

    @NotNull(message = "Licence dateOfIssue must not be null")
    private Timestamp dateOfIssue;

    @NotNull(message = "Licence validUntil must not be null")
    private Timestamp validUntil;

    @NotNull(message = "Licence categoryB must not be null")
    @AssertTrue(message = "You cannot add a license without a category B")
    private Boolean categoryB;

    @NotNull(message = "Licence serialNumber must not be null")
    @Size(min = 9, max = 9, message = "The serial number must contain 9 numbers and letters")
    private String serialNumber;

    @NotNull(message = "Licence userId must not be null")
    @Pattern(regexp = "^\\d*", message = "User id must contain only numbers")
    private String userId;
}
