package by.logonuk.controller;

import by.logonuk.controller.converters.car.request.CarCreateMapper;
import by.logonuk.controller.converters.car.request.CarUpdateMapper;
import by.logonuk.controller.requests.CarCreateRequest;
import by.logonuk.controller.requests.CarUpdateRequest;
import by.logonuk.controller.responses.CarResponse;
import by.logonuk.domain.Car;
import by.logonuk.domain.attachments.TechnicalInfo;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.CarRepository;
import by.logonuk.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    public final CarRepository repository;

    private final CarService carService;

    private final ConversionService converter;

    private static final String RESULT = "result";

    private static final String CAR_NOT_FOUND = "Car with this %s = %d does not exist";

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        long carId = Long.parseLong(id);
        Optional<Car> searchCar = repository.findByIdAndTechnicalInfoIsDeleted(carId, false);
        Car car = searchCar.orElseThrow(() -> new NoSuchEntityException(CAR_NOT_FOUND.formatted("id", carId)));
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(car, CarResponse.class)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAllCars() {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.findAllByTechnicalInfoIsDeleted(false).stream().map(i -> converter.convert(i, CarResponse.class)).collect(Collectors.toList())), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> createCar(@Valid @RequestBody CarCreateRequest carCreateRequest) {

        Timestamp timestamp = new Timestamp(new Date().getTime());
        TechnicalInfo technicalInfo = new TechnicalInfo(timestamp, timestamp, false);
        CarCreateMapper carCreateMapper = new CarCreateMapper(carCreateRequest, technicalInfo);
        Car car = carService.createCar(carCreateMapper.carMapping(), carCreateMapper.modelMapping(), carCreateMapper.classificationMapping(), carCreateMapper.carManufactureMapping());
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(car, CarResponse.class)), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Object> updateCar(@Valid @RequestBody CarUpdateRequest carUpdateRequest) {
        CarUpdateMapper carCreateMapping = new CarUpdateMapper(repository, carUpdateRequest, new Timestamp((new Date().getTime())));
        Car updatedCar = carService.updateCar(carCreateMapping.carMapping(), carCreateMapping.modelMapping(), carCreateMapping.classificationMapping(), carCreateMapping.carManufactureMapping());
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(updatedCar, CarResponse.class)), HttpStatus.CREATED);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Object> softCarDelete(@PathVariable String id) {
        long carId = Long.parseLong(id);
        Optional<Car> searchCar = repository.findByIdAndTechnicalInfoIsDeleted(carId, false);
        Car car = searchCar.orElseThrow(()-> new NoSuchEntityException(CAR_NOT_FOUND.formatted("id", carId)));
        car.getTechnicalInfo().setIsDeleted(true);
        repository.save(car);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(car, CarResponse.class)), HttpStatus.OK);
    }
}
