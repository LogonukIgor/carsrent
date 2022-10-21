package by.logonuk.controller.requests;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DealCreateRequest {

    private String userId;

    private String carId;

    private Timestamp receivingDate;

    private Timestamp returnDate;
}
