package by.logonuk.controller;

import by.logonuk.controller.requests.CarCreateRequest;
import by.logonuk.controller.requests.UserCreateRequest;
import by.logonuk.domain.Car;
import by.logonuk.domain.User;
import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import by.logonuk.domain.embed.user.Credentials;
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

        Car car = new Car();
        car.setEngineVolume(carCreateRequest.getEngineVolume());
        car.setCostPerDay(carCreateRequest.getCostPerDay());
        car.setNumberOfSeats(carCreateRequest.getNumberOfSeats());
        car.setCostPerDay(carCreateRequest.getCostPerDay());
        if(carCreateRequest.getTransmission().equals(Transmissions.AUTOMATIC.toString())){
            car.setTransmission(Transmissions.AUTOMATIC);
        }else if(carCreateRequest.getTransmission().equals(Transmissions.MANUAL.toString())){
            car.setTransmission(Transmissions.MANUAL);
        }else {
            return new ResponseEntity<>(Collections.singletonMap("result", "Not valid transmission"), HttpStatus.BAD_REQUEST)
        }

        TechnicalDatesAndInfo techDateInf = new TechnicalDatesAndInfo(timestamp, timestamp, false);

        carService.createCar(car);

        return new ResponseEntity<>(Collections.singletonMap("result", "Successful user addition. Check your mail"), HttpStatus.CREATED);
    }
}
