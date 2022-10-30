package by.logonuk.controller.requests.licence;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "Request for licence update")
public class DrivingLicenceUpdateRequest extends DrivingLicenceCreateRequest{

    @NotNull(message = "Licence id must not be null")
    @Pattern(regexp = "^\\d*$", message = "Licence id must contain only numbers")
    @Schema(description = "Licence id", required = true, type = "string")
    private String licenceId;
}
