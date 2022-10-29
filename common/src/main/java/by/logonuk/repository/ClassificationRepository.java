package by.logonuk.repository;

import by.logonuk.domain.Classification;
import by.logonuk.domain.Role;
import by.logonuk.domain.enums.ClassificationLetter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassificationRepository extends JpaRepository<Classification, Integer> {

    @Cacheable(cacheNames = "classification", cacheManager = "cacheManagerClassification")
    Classification findByClassificationLetter(ClassificationLetter classificationLetter);

    @Cacheable(cacheNames = "classification", cacheManager = "cacheManagerClassification")
    List<Classification> findAll();
}
