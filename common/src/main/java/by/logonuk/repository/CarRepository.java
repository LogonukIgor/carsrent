package by.logonuk.repository;

import by.logonuk.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long>{

    Optional<Car> findByIdAndTechnicalInfoIsDeleted(Long id, Boolean isDeleted);

    Optional<Car> findByIdAndTechnicalInfoIsDeletedAndIsInStock(Long id, Boolean isDeleted, Boolean isInStock);

    List<Car> findAllByTechnicalInfoIsDeleted(Boolean isDeleted);

    @Query(value = "select cars_rent.get_cars_stats_number_of_cars_in_stock()", nativeQuery = true)
    Map<String, Integer> carsInStock();
}
