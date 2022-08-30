package by.logonuk.repository.car;

import by.logonuk.domain.Car;
import by.logonuk.domain.User;
import by.logonuk.repository.CRUDRepository;

import java.util.Map;
import java.util.Optional;

public interface CarRepositoryInterface extends CRUDRepository<Long, Car> {

    Map<String, Integer> carsInStock();
}
