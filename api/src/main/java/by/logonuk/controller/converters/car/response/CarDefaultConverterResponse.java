package by.logonuk.controller.converters.car.response;

import by.logonuk.controller.responses.car.CarResponse;
import by.logonuk.domain.Car;
import org.springframework.stereotype.Component;

@Component
public class CarDefaultConverterResponse extends CarBaseConverterResponse<Car, CarResponse> {
    @Override
    public CarResponse convert(Car source) {
        CarResponse carResponse = new CarResponse();
        return doConvert(carResponse, source);
    }
}
