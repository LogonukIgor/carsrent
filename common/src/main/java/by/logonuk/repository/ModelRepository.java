package by.logonuk.repository;

import by.logonuk.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Integer> {
}
