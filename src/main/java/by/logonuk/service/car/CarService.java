package by.logonuk.service.car;

import by.logonuk.domain.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CarService {

    List<Car> findAll();

    Car findById(Long id);

    Optional<Car> findOne(Long id);

    List<Car> findAll(int limit, int offset);

    Car create(Car object);

    Car update(Car object);

    Long delete(Long id);

    Map<String, Integer> carsInStock();
}
