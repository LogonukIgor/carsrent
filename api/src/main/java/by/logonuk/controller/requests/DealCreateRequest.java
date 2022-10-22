package by.logonuk.controller.requests;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
public class DealCreateRequest {

    @NotNull(message = "Deal userId must not be null")
    @Pattern(regexp = "^\\d*", message = "User id must contain only numbers")
    private String userId;

//    @NotNull(message = "Deal carId must not be null")
    @Pattern(regexp = "^\\d*", message = "Car id must contain only numbers")
    private String carId;

//    @FutureOrPresent(message = "Receiving date value must be in the future or present")
    @NotNull(message = "Deal receivingDate must not be null")
    private Timestamp receivingDate;

    @Future(message = "Return date value must be in the future")
    @NotNull(message = "Deal returnDate must not be null")
    private Timestamp returnDate;
}
