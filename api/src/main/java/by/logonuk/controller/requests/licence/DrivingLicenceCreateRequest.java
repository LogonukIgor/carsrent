package by.logonuk.controller.requests.licence;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@Schema(description = "Request for licence create")
public class DrivingLicenceCreateRequest {

    @NotNull(message = "Licence dateOfIssue must not be null")
    @Schema(description = "Date of issue", required = true, type = "timestamp")
    private Timestamp dateOfIssue;

    @NotNull(message = "Licence validUntil must not be null")
    @Schema(description = "Date until which the license is valid", required = true, type = "timestamp")
    private Timestamp validUntil;

    @NotNull(message = "Licence categoryB must not be null")
    @AssertTrue(message = "You cannot add a license without a category B")
    @Schema(description = "Is there a category B?", required = true, type = "boolean")
    private Boolean categoryB;

    @NotNull(message = "Licence serialNumber must not be null")
    @Size(min = 9, max = 9, message = "The serial number must contain 9 numbers and letters")
    @Pattern(regexp = "^\\d[A-Z]{3}\\d{5}$", message = "The serial number must be in this pattern: 0AAA00000")
    @Schema(description = "Serial number", required = true, type = "string")
    private String serialNumber;

    @NotNull(message = "Licence userId must not be null")
    @Pattern(regexp = "^\\d*$", message = "User id must contain only numbers")
    @Schema(description = "User id", required = true, type = "string")
    private String userId;
}
