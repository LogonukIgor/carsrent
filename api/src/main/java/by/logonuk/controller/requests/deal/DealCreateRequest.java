package by.logonuk.controller.requests.deal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@Schema(description = "Request for deal create")
public class DealCreateRequest {

    @NotNull(message = "Deal userId must not be null")
    @Pattern(regexp = "^\\d*$", message = "User id must contain only numbers")
    @Schema(description = "User id", required = true, type = "string")
    private String userId;

    @NotNull(message = "Deal carId must not be null")
    @Pattern(regexp = "^\\d*$", message = "Car id must contain only numbers")
    @Schema(description = "Car id", required = true, type = "string")
    private String carId;

    @NotNull(message = "Deal receivingDate must not be null")
    @Schema(description = "Receiving date", required = true, type = "timestamp")
    private Timestamp receivingDate;

    @Future(message = "Return date value must be in the future")
    @NotNull(message = "Deal returnDate must not be null")
    @Schema(description = "Return date", required = true, type = "timestamp")
    private Timestamp returnDate;
}
