package by.logonuk.repository;

import by.logonuk.domain.CarManufactury;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarManufacturyRepository extends JpaRepository<CarManufactury, Integer> {

    Optional<CarManufactury> findByCountryOfOrigin(String countryOfOrigin);
}
