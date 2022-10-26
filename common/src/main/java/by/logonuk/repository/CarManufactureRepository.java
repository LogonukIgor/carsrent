package by.logonuk.repository;

import by.logonuk.domain.CarManufacture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarManufactureRepository extends JpaRepository<CarManufacture, Integer> {

    Optional<CarManufacture> findByCountryOfOriginAndBrand(String countryOfOrigin, String brand);
}
