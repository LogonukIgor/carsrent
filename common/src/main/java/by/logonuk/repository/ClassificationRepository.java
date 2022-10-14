package by.logonuk.repository;

import by.logonuk.domain.Classification;
import by.logonuk.domain.enums.ClassificationLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificationRepository extends JpaRepository<Classification, Integer> {

    Classification findByClassificationLetter(ClassificationLetter classificationLetter);
}
