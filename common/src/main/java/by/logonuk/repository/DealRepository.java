package by.logonuk.repository;

import by.logonuk.domain.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Long>{

    List<Deal> findAllByTechnicalInfoIsDeleted(Boolean isDeleted);

    Optional<Deal> findByIdAndTechnicalInfoIsDeleted(Long dealId, Boolean isDeleted);

    @Query(value = "select cars_rent.get_deal_stats_number_of_open_deal()", nativeQuery = true)
    Map<String, Integer> CountOfDeals();
}
