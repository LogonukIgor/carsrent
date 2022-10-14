package by.logonuk.controller.mapping;

import by.logonuk.controller.requests.CarCreateRequest;
import by.logonuk.domain.Car;
import by.logonuk.domain.CarManufactury;
import by.logonuk.domain.Classification;
import by.logonuk.domain.Model;
import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import by.logonuk.domain.enums.ClassificationLetter;
import by.logonuk.domain.enums.Transmissions;

public class CarMapping {
    public static Car carMapping(CarCreateRequest carCreateRequest, TechnicalDatesAndInfo technicalDatesAndInfo) throws IllegalArgumentException{
        Car car = new Car();
        car.setEngineVolume(carCreateRequest.getEngineVolume());
        car.setYearOfIssue(2001);
        car.setAirConditioner(true);
        car.setNumberOfSeats(carCreateRequest.getNumberOfSeats());
        car.setCostPerDay(carCreateRequest.getCostPerDay());
        car.setTransmission(Transmissions.valueOf(carCreateRequest.getTransmission()));
        car.setTechnicalDatesAndInfo(technicalDatesAndInfo);
        car.setIsInStock(true);
        return car;
    }

    public static CarManufactury carManufacturyMapping(CarCreateRequest carCreateRequest, TechnicalDatesAndInfo technicalDatesAndInfo){
        CarManufactury carManufactury = new CarManufactury();
        carManufactury.setBrand(carCreateRequest.getBrand());
        carManufactury.setCountryOfOrigin(carCreateRequest.getCountryOfOrigin());
        carManufactury.setTechnicalDatesAndInfo(technicalDatesAndInfo);
        return carManufactury;
    }
    public static Model modelMapping(CarCreateRequest carCreateRequest, TechnicalDatesAndInfo technicalDatesAndInfo){
        Model model = new Model();
        model.setModelName(carCreateRequest.getModel());
        model.setTechnicalDatesAndInfo(technicalDatesAndInfo);
        return model;
    }
    public static Classification classificationMapping(CarCreateRequest carCreateRequest) throws IllegalArgumentException{
        Classification classification = new Classification();
        classification.setClassificationLetter(ClassificationLetter.valueOf(carCreateRequest.getClassification()));
        return classification;
    }
}
