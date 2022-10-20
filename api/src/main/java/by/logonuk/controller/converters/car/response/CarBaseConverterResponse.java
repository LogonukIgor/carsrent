package by.logonuk.controller.converters.car.response;

import by.logonuk.controller.responses.CarParameters;
import by.logonuk.controller.responses.CarResponse;
import by.logonuk.domain.Car;
import org.springframework.core.convert.converter.Converter;

public abstract class CarBaseConverterResponse<S, T> implements Converter<S, T> {

    public CarResponse doConvert(CarResponse carResponse, Car source) {

        carResponse.setId(source.getId());
        carResponse.setIsInStock(source.getIsInStock());
        carResponse.setCostPerDay(source.getCostPerDay());
        carResponse.setCarInfo(source.getCarInfo());

        CarParameters carParameters = new CarParameters();
        carParameters.setEngineVolume(source.getEngineVolume());
        carParameters.setDateOfIssue(source.getDateOfIssue());
        carParameters.setNumberOfSeats(source.getNumberOfSeats());
        carParameters.setAirConditioner(source.getAirConditioner());
        carParameters.setTransmission(source.getTransmission().toString());

        carResponse.setCarParameters(carParameters);
        return carResponse;
    }
}
