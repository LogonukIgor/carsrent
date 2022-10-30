package by.logonuk.controller.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CarUpdateRequest extends CarCreateRequest {

    @NotNull(message = "Car id must not be null")
    @Pattern(regexp = "^\\d*$", message = "Car id must contain only numbers")
    private String carId;
}
