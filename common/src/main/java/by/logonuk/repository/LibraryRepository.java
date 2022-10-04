package by.logonuk.repository;

import by.logonuk.domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
