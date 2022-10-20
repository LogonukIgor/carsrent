package by.logonuk.controller.responses;

import by.logonuk.domain.Library;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;

@Data
@ApiModel(description = "car response")
public class CarResponse {

    private Long id;

    private Boolean isInStock;

    private Double costPerDay;

    private CarParameters carParameters;

    private Library carInfo;
}
