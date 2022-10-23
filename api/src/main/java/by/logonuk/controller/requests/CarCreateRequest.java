package by.logonuk.controller.requests;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@ApiModel(description = "Model for car request")
public class CarCreateRequest {

    @NotNull(message = "Car engineVolume must not be null")
    @Min(value = 1, message = "number(engineVolume)  must be >= 1")
    private Double engineVolume;

    @NotNull(message = "Car dateOfIssue must not be null")
    private Timestamp dateOfIssue;

    @NotNull(message = "Car numberOfSeats must not be null")
    @Min(value = 2, message = "number(numberOfSeats)  must be >= 2")
    private Integer numberOfSeats;

    @NotNull(message = "Car costPerDay must not be null")
    @Min(value = 10, message = "number(costPerDay)  must be >= 10")
    private Double costPerDay;

    @NotNull(message = "Car transmission must not be null")
    @Size(min = 2, max = 15, message = "Model size must be between 2 and 15")
    private String transmission;

    @NotNull(message = "Car model must not be null")
    @Size(min = 2, max = 20, message = "Model size must be between 2 and 20")
    private String model;

    @NotNull(message = "Car classification must not be null")
    @Size(max = 1, message = "Classification size must be 1")
    private String classification;

    @NotNull(message = "Car brand must not be null")
    @Size(min = 2, max = 20, message = "Brand size must be between 2 and 20")
    private String brand;

    @NotNull(message = "Car countryOfOrigin must not be null")
    @Size(min = 2, max = 20, message = "Country of origin size must be between 2 and 20")
    @Pattern(regexp = "^[A-Z][a-z]*", message = "Country of origin must start with a capital letter and contain only letters")
    private String countryOfOrigin;

    @NotNull(message = "Car airConditioner must not be null")
    private Boolean airConditioner;
}
