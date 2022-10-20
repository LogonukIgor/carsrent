package by.logonuk.repository;

import by.logonuk.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long>{

    Optional<Car> findByIdAndTechnicalInfoIsDeleted(Long id, Boolean isDeleted);

    List<Car> findAllByTechnicalInfoIsDeleted(Boolean isDeleted);
}
