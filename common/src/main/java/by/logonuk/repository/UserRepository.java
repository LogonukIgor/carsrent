package by.logonuk.repository;

import by.logonuk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByCredentialsLogin(String login);

    User findByUserName(String username);

    @Modifying
    @Query(value = "insert into cars_rent.l_role_user(user_id, role_id) values (:user_id, :role_id)", nativeQuery = true)
    void getRoleToUser(@Param("user_id")Long userId, @Param("role_id")Long roleId);

    Optional<User> findByActivationCode(String activationCode);
}