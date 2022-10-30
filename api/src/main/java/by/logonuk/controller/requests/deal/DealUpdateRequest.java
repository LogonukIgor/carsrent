package by.logonuk.controller.requests.deal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "Request for deal update")
public class DealUpdateRequest extends DealCreateRequest{

    @NotNull(message = "Deal id must not be null")
    @Pattern(regexp = "^\\d*$", message = "Deal id must contain only numbers")
    @Schema(description = "Deal id", required = true, type = "string")
    private Long dealId;
}
