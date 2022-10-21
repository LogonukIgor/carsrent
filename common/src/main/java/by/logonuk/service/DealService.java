package by.logonuk.service;

import by.logonuk.domain.Car;
import by.logonuk.domain.User;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.CarRepository;
import by.logonuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DealService {

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    public Map<String, Object> validateDeal(String userIdRequest, String carIdRequest){
        Long userId = Long.parseLong(userIdRequest);
        Long carId = Long.parseLong(carIdRequest);

        Optional<User> searchUser = userRepository.findByIdAndTechnicalInfoIsDeleted(userId, false);
        Optional<Car> searchCar = carRepository.findByIdAndTechnicalInfoIsDeleted(carId, false);

        User user = searchUser.orElseThrow(()-> new NoSuchEntityException("User with id = " + userId + " does not exist"));
        Car car = searchCar.orElseThrow(()-> new NoSuchEntityException("Car with id = " + carId + " does not exist"));

        Map<String, Object> entityMap = new HashMap<>(2);
        entityMap.put("user", user);
        entityMap.put("car", car);
        return entityMap;
    }
}
