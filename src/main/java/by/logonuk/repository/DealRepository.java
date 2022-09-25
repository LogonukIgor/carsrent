package by.logonuk.repository;

import by.logonuk.domain.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DealRepository extends CrudRepository<Deal, Long>, JpaRepository<Deal, Long>, PagingAndSortingRepository<Deal, Long>{
}
