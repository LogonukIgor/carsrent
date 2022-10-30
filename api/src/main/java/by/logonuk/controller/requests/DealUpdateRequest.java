package by.logonuk.controller.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class DealUpdateRequest extends DealCreateRequest{

    @NotNull(message = "Deal id must not be null")
    @Pattern(regexp = "^\\d*$", message = "Deal id must contain only numbers")
    private Long dealId;
}
