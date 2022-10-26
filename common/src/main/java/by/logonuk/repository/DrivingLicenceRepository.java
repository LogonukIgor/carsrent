package by.logonuk.repository;

import by.logonuk.domain.DrivingLicence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DrivingLicenceRepository extends JpaRepository<DrivingLicence, Long> {

    @Query(value = "select * from cars_rent.driving_licence where user_id = :user_id", nativeQuery = true)
    Optional<DrivingLicence> customFindByUserId(@Param("user_id") Long userId);

    Optional<DrivingLicence> findByIdAndTechnicalInfoIsDeleted(Long licenceId, Boolean isDeleted);
}
