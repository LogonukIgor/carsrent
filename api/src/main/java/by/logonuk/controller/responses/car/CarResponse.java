package by.logonuk.controller.responses.car;

import by.logonuk.domain.Library;
import lombok.Data;

@Data
public class CarResponse {

    private Long id;

    private Boolean isInStock;

    private Double costPerDay;

    private CarParameters carParameters;

    private Library carInfo;
}
