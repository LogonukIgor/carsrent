package by.logonuk.controller.mapping;

import by.logonuk.controller.requests.CarCreateRequest;
import by.logonuk.controller.requests.CarUpdateRequest;
import by.logonuk.domain.Car;
import by.logonuk.domain.CarManufacture;
import by.logonuk.domain.Classification;
import by.logonuk.domain.Model;
import by.logonuk.domain.embed.TechnicalInfo;
import by.logonuk.domain.enums.ClassificationLetter;
import by.logonuk.domain.enums.Transmissions;
import by.logonuk.repository.CarRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Data
public class CarUpdateMapping {

    private CarRepository carRepository;

    private CarUpdateRequest carUpdateRequest;

    private Timestamp timestamp;

    public CarUpdateMapping(@Autowired CarRepository carRepository, CarUpdateRequest carUpdateRequest, Timestamp timestamp) {
        this.carRepository = carRepository;
        this.carUpdateRequest = carUpdateRequest;
        this.timestamp = timestamp;
    }

    public CarUpdateMapping(CarUpdateRequest carUpdateRequest, Timestamp timestamp) {
        this.carUpdateRequest = carUpdateRequest;
        this.timestamp = timestamp;
    }

    public Car carMapping() throws IllegalArgumentException {
        Car car = new Car();
        car.setId(Long.parseLong(carUpdateRequest.getCarId()));
        car.setEngineVolume(carUpdateRequest.getEngineVolume());
        car.setDateOfIssue(carUpdateRequest.getDateOfIssue());
        car.setAirConditioner(carUpdateRequest.getAirConditioner());
        car.setNumberOfSeats(carUpdateRequest.getNumberOfSeats());
        car.setCostPerDay(carUpdateRequest.getCostPerDay());
        car.setTransmission(Transmissions.valueOf(carUpdateRequest.getTransmission().toUpperCase()));
        car.setIsInStock(true);
        Optional<Car> searchCar = carRepository.findByIdAndTechnicalInfoIsDeleted(car.getId(), false);
        if (searchCar.isPresent()) {
            Car originalCar = searchCar.get();
            TechnicalInfo technicalInfo = originalCar.getTechnicalInfo();
            technicalInfo.setModificationDate(new Timestamp(new Date().getTime()));
            car.setTechnicalInfo(technicalInfo);
        }
        return car;
    }

    public CarManufacture carManufactureMapping() {
        TechnicalInfo technicalInfoNow = new TechnicalInfo(timestamp, timestamp, false);
        CarManufacture carManufacture = new CarManufacture();
        carManufacture.setBrand(carUpdateRequest.getBrand());
        carManufacture.setCountryOfOrigin(carUpdateRequest.getCountryOfOrigin());
        carManufacture.setTechnicalInfo(technicalInfoNow);
        return carManufacture;
    }

    public Model modelMapping() {
        TechnicalInfo technicalInfoNow = new TechnicalInfo(timestamp, timestamp, false);
        Model model = new Model();
        model.setModelName(carUpdateRequest.getModel());
        model.setTechnicalInfo(technicalInfoNow);
        return model;
    }

    public Classification classificationMapping() throws IllegalArgumentException {
        Classification classification = new Classification();
        classification.setClassificationLetter(ClassificationLetter.valueOf(carUpdateRequest.getClassification().toUpperCase()));
        return classification;
    }
}
