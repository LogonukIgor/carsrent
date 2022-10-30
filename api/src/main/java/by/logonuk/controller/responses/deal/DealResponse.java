package by.logonuk.controller.responses.deal;

import by.logonuk.controller.responses.car.CarResponse;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DealResponse {

    private Long id;

    private Timestamp receivingDate;

    private Timestamp returnDate;

    private Double price;

    private CarResponse car;
}
