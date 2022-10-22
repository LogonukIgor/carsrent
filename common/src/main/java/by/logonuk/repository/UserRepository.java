package by.logonuk.repository;

import by.logonuk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCredentialsLoginAndTechnicalInfoIsDeleted(String login, Boolean isDeleted);

    Optional<User> findByIdAndTechnicalInfoIsDeleted(Long id, Boolean isDeleted);

    List<User> findAllByTechnicalInfoIsDeleted(Boolean isDeleted);

    Optional<User> findByActivationCodeAndTechnicalInfoIsDeleted(String activationCode, Boolean isDeleted);

    User findByUserNameAndTechnicalInfoIsDeleted(String username, Boolean isDeleted);

    @Modifying
    @Query(value = "insert into cars_rent.l_role_user(user_id, role_id) values (:user_id, :role_id)", nativeQuery = true)
    void getRoleToUser(@Param("user_id") Long userId, @Param("role_id") Long roleId);

    @Modifying
    @Query(value = "delete from cars_rent.users where id = :user_id", nativeQuery = true)
    void customUserDelete(@Param("user_id") Long userId);

//    @Query(value = "select u from User u inner join Deal d on d.user = u.deal where d.id = :deal_id")
//    User findByDeal(@Param("deal_id") Long dealId);

    @Query(value = "select * from cars_rent.users as u inner join cars_rent.deal as d on  d.user_id = u.id where d.id = :deal_id, d.is_deleted = :is_deleted", nativeQuery = true)
    User findByDeal(@Param("deal_id") Long dealId, @Param("is_deleted") Boolean isDeleted);
}