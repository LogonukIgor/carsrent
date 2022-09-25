package by.logonuk.repository;

import by.logonuk.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CarRepository extends CrudRepository<Car, Long>, JpaRepository<Car, Long>, PagingAndSortingRepository<Car, Long> {
}
