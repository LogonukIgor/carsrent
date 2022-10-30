package by.logonuk.controller.requests.car;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@Schema(description = "Request for car create")
public class CarCreateRequest {

    @NotNull(message = "Car engineVolume must not be null")
    @Min(value = 1, message = "number(engineVolume)  must be >= 1")
    @Schema(description = "Engine volume", required = true, type = "double")
    private Double engineVolume;

    @NotNull(message = "Car dateOfIssue must not be null")
    @Schema(description = "Date of issue", required = true, type = "timestamp")
    private Timestamp dateOfIssue;

    @NotNull(message = "Car numberOfSeats must not be null")
    @Min(value = 2, message = "number(numberOfSeats)  must be >= 2")
    @Schema(description = "Number of seats", required = true, type = "int")
    private Integer numberOfSeats;

    @NotNull(message = "Car costPerDay must not be null")
    @Min(value = 10, message = "number(costPerDay)  must be >= 10")
    @Schema(description = "Cost per day", required = true, type = "double")
    private Double costPerDay;

    @NotNull(message = "Car transmission must not be null")
    @Size(min = 2, max = 15, message = "Model size must be between 2 and 15")
    @Schema(description = "Type of transmission", required = true, type = "string")
    private String transmission;

    @NotNull(message = "Car model must not be null")
    @Size(min = 2, max = 20, message = "Model size must be between 2 and 20")
    @Schema(description = "Model", required = true, type = "string")
    private String model;

    @NotNull(message = "Car classification must not be null")
    @Size(max = 1, message = "Classification size must be 1")
    @Schema(description = "Classification letter(a, b, c, d, e, f, j, s)", required = true, type = "string")
    private String classification;

    @NotNull(message = "Car brand must not be null")
    @Size(min = 2, max = 20, message = "Brand size must be between 2 and 20")
    @Schema(description = "Car brand", required = true, type = "string")
    private String brand;

    @NotNull(message = "Car countryOfOrigin must not be null")
    @Size(min = 2, max = 20, message = "Country of origin size must be between 2 and 20")
    @Pattern(regexp = "^[a-zA-Z, ]*", message = "Country of origin must start with a capital letter and contain only letters")
    @Schema(description = "Country of origin", required = true, type = "string")
    private String countryOfOrigin;

    @NotNull(message = "Car airConditioner must not be null")
    @Schema(description = "Country of origin", required = true, type = "boolean")
    private Boolean airConditioner;
}
