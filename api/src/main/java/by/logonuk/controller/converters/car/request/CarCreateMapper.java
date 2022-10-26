package by.logonuk.controller.converters.car.request;

import by.logonuk.controller.requests.CarCreateRequest;
import by.logonuk.domain.Car;
import by.logonuk.domain.CarManufacture;
import by.logonuk.domain.Classification;
import by.logonuk.domain.Model;
import by.logonuk.domain.attachments.TechnicalInfo;
import by.logonuk.domain.enums.ClassificationLetter;
import by.logonuk.domain.enums.Transmissions;
import by.logonuk.exception.CustomIllegalArgumentException;
import lombok.Data;

@Data
public class CarCreateMapper {

    private CarCreateRequest carCreateRequest;

    private TechnicalInfo technicalInfo;

    public CarCreateMapper(CarCreateRequest carCreateRequest, TechnicalInfo technicalInfo) {
        this.carCreateRequest = carCreateRequest;
        this.technicalInfo = technicalInfo;
    }

    public Car carMapping() throws IllegalArgumentException {
        Car car = new Car();
        car.setEngineVolume(carCreateRequest.getEngineVolume());
        car.setDateOfIssue(carCreateRequest.getDateOfIssue());
        car.setAirConditioner(true);
        car.setNumberOfSeats(carCreateRequest.getNumberOfSeats());
        car.setCostPerDay(carCreateRequest.getCostPerDay());
        try {
        car.setTransmission(Transmissions.valueOf(carCreateRequest.getTransmission().toUpperCase()));
        }catch (IllegalArgumentException e){
            throw new CustomIllegalArgumentException("Transmission must be selected from this list: automatic, manual");
        }
        car.setTechnicalInfo(technicalInfo);
        car.setIsInStock(true);
        return car;
    }

    public CarManufacture carManufactureMapping() {
        CarManufacture carManufacture = new CarManufacture();
        carManufacture.setBrand(carCreateRequest.getBrand());
        carManufacture.setCountryOfOrigin(carCreateRequest.getCountryOfOrigin());
        carManufacture.setTechnicalInfo(technicalInfo);
        return carManufacture;
    }

    public Model modelMapping() {
        Model model = new Model();
        model.setModelName(carCreateRequest.getModel());
        model.setTechnicalInfo(technicalInfo);
        return model;
    }

    public Classification classificationMapping(){
        Classification classification = new Classification();
        try {
            classification.setClassificationLetter(ClassificationLetter.valueOf(carCreateRequest.getClassification().toUpperCase()));
        }catch (IllegalArgumentException e){
            throw new CustomIllegalArgumentException("Classification must be selected from this list: a, b, c, d, e, f, j, s");
        }
        return classification;
    }
}
