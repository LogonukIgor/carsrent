package by.logonuk.controller;

import by.logonuk.controller.mapping.CarMapping;
import by.logonuk.controller.requests.CarCreateRequest;
import by.logonuk.domain.Car;
import by.logonuk.domain.CarManufactury;
import by.logonuk.domain.Classification;
import by.logonuk.domain.Model;
import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import by.logonuk.domain.enums.ClassificationLetter;
import by.logonuk.domain.enums.Transmissions;
import by.logonuk.repository.CarRepository;
import by.logonuk.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    public final CarRepository repository;

    private final CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id){
        long userId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("result", repository.findById(userId)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> findAllCars(){
        return new ResponseEntity<>(Collections.singletonMap("result", repository.findAll()), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> createCar(@RequestBody CarCreateRequest carCreateRequest){

        Timestamp timestamp = new Timestamp(new Date().getTime());
        TechnicalDatesAndInfo carTechDateInf = new TechnicalDatesAndInfo(timestamp, timestamp, false);

        Car car = CarMapping.carMapping(carCreateRequest, carTechDateInf);

        CarManufactury carManufactury = CarMapping.carManufacturyMapping(carCreateRequest, carTechDateInf);

        Classification classification = CarMapping.classificationMapping(carCreateRequest);

        Model model = CarMapping.modelMapping(carCreateRequest, carTechDateInf);

        carService.createCar(car, model, classification, carManufactury, carTechDateInf);
        return new ResponseEntity<>(Collections.singletonMap("result", "Successful car addition"), HttpStatus.CREATED);
    }
}
