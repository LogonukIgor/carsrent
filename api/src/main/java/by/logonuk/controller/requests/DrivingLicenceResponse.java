package by.logonuk.controller.requests;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DrivingLicenceResponse{

    private Long id;

    private Timestamp dateOfIssue;

    private Timestamp validUntil;

    private Boolean categoryB;

    private String serialNumber;

    private Long userId;
}
