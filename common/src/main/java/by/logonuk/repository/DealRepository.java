package by.logonuk.repository;

import by.logonuk.domain.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Long>{

    List<Deal> findAllByTechnicalInfoIsDeleted(Boolean isDeleted);

    Optional<Deal> findByIdAndTechnicalInfoIsDeleted(Long dealId, Boolean isDeleted);
}
