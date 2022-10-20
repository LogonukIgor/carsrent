package by.logonuk.controller.responses;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CarParameters {

    private Double engineVolume;

    private Timestamp dateOfIssue;

    private Integer numberOfSeats;

    private Boolean airConditioner;

    private String transmission;
}
