package by.logonuk.service;

import by.logonuk.domain.Car;
import by.logonuk.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public void createCar(Car car){

    }
}
