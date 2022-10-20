package by.logonuk.repository;

import by.logonuk.domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    @Modifying
    @Query(value = "insert into cars_rent.l_brand_model_class(brand_id, model_id, classification_id, car_id) values (:brand_id, :model_id, :classification_id, :car_id)", nativeQuery = true)
    void customSave(@Param("brand_id") Integer brandId, @Param("model_id") Integer modelId, @Param("classification_id") Integer classificationId, @Param("car_id") Long carId);

    @Query(value = "select * from cars_rent.l_brand_model_class where car_id = :car_id", nativeQuery = true)
    Library findByCarId(@Param("car_id") Long carId);
}
