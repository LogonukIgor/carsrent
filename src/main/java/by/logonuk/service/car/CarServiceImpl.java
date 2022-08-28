package by.logonuk.service.car;

import by.logonuk.domain.Car;
import by.logonuk.repository.car.CarRepositoryInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService{

    private CarRepositoryInterface carRepository;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Optional<Car> findOne(Long id) {
        return carRepository.findOne(id);
    }

    @Override
    public List<Car> findAll(int limit, int offset) {
        return carRepository.findAll(limit, offset);
    }

    @Override
    public Car create(Car object) {
        return carRepository.create(object);
    }

    @Override
    public Car update(Car object) {
        return carRepository.update(object);
    }

    @Override
    public Long delete(Long id) {
        return carRepository.delete(id);
    }
}
