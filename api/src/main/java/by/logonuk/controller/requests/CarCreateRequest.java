package by.logonuk.controller.requests;

import lombok.Data;

@Data
public class CarCreateRequest {

    private Double engineVolume;

    private Integer yearOfIssue;

    private Integer numberOfSeats;

    private Double costPerDay;

    private String transmission;
}
