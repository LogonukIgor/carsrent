package by.logonuk.controller.responses;

import by.logonuk.domain.Car;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DealResponse {

    private Long id;

    private Timestamp receivingDate;

    private Timestamp returnDate;

    private Double price;

    private Car dealCar;
}
