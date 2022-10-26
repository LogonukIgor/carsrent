package by.logonuk.controller.responses;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DrivingLicenceResponse{

    private Long id;

    private Timestamp dateOfIssue;

    private Timestamp validUntil;

    private String serialNumber;

    private Long userId;
}
