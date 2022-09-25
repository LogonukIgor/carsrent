package by.logonuk.repository;

import by.logonuk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {
}
