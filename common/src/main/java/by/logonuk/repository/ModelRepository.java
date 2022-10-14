package by.logonuk.repository;

import by.logonuk.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Integer> {

    Optional<Model> findByModelName(String modelName);
}
