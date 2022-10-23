package by.logonuk.controller.converters.car.request;

import by.logonuk.controller.requests.CarUpdateRequest;
import by.logonuk.domain.Car;
import by.logonuk.domain.CarManufacture;
import by.logonuk.domain.Classification;
import by.logonuk.domain.Model;
import by.logonuk.domain.embed.TechnicalInfo;
import by.logonuk.domain.enums.ClassificationLetter;
import by.logonuk.domain.enums.Transmissions;
import by.logonuk.exception.CustomIllegalArgumentException;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.CarRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Data
@AllArgsConstructor
public class CarUpdateMapper {

    private CarRepository carRepository;

    private CarUpdateRequest carUpdateRequest;

    private Timestamp timestamp;

    public Car carMapping() throws IllegalArgumentException {
        Car car = new Car();

        car.setId(Long.parseLong(carUpdateRequest.getCarId()));
        car.setEngineVolume(carUpdateRequest.getEngineVolume());
        car.setDateOfIssue(carUpdateRequest.getDateOfIssue());
        car.setAirConditioner(carUpdateRequest.getAirConditioner());
        car.setNumberOfSeats(carUpdateRequest.getNumberOfSeats());
        car.setCostPerDay(carUpdateRequest.getCostPerDay());
        car.setIsInStock(true);

        try {
            car.setTransmission(Transmissions.valueOf(carUpdateRequest.getTransmission().toUpperCase()));
        }catch (IllegalArgumentException e){
            throw new CustomIllegalArgumentException("Transmission must be selected from this list: automatic, manual");
        }

        Optional<Car> searchCar = carRepository.findByIdAndTechnicalInfoIsDeleted(Long.parseLong(carUpdateRequest.getCarId()), false);
        Car originalCar = searchCar.orElseThrow(() -> new NoSuchEntityException("Car with id = " + carUpdateRequest.getCarId() + " does not exist"));

        TechnicalInfo technicalInfo = originalCar.getTechnicalInfo();
        technicalInfo.setModificationDate(new Timestamp(new Date().getTime()));
        car.setTechnicalInfo(technicalInfo);

        car.setId(Long.parseLong(carUpdateRequest.getCarId()));
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
        try {
            classification.setClassificationLetter(ClassificationLetter.valueOf(carUpdateRequest.getClassification().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new CustomIllegalArgumentException("Classification must be selected from this list: a, b, c, d, e, f, j, s");
        }
        return classification;
    }
}
