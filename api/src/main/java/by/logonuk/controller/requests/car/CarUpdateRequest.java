package by.logonuk.controller.requests.car;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "Request for car update")
public class CarUpdateRequest extends CarCreateRequest {

    @NotNull(message = "Car id must not be null")
    @Pattern(regexp = "^\\d*$", message = "Car id must contain only numbers")
    @Schema(description = "Car id", required = true, type = "string")
    private String carId;
}
