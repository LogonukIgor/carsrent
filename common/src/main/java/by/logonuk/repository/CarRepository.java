package by.logonuk.repository;

import by.logonuk.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long>{
}
