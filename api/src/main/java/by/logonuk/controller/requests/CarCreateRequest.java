package by.logonuk.controller.requests;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.sql.Timestamp;

@Data
@ApiModel(description = "Model for car request")
public class CarCreateRequest {

    private Double engineVolume;

    private Timestamp dateOfIssue;

    private Integer numberOfSeats;

    private Double costPerDay;

    private String transmission;

    private String model;

    private String classification;

    private String brand;

    private String countryOfOrigin;

    private Boolean airConditioner;
}
