package by.logonuk.controller.requests;

import lombok.Data;

@Data
public class CarUpdateRequest extends CarCreateRequest {

    private String carId;
}
