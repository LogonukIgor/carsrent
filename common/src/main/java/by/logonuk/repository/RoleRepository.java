package by.logonuk.repository;

import by.logonuk.domain.Role;
import by.logonuk.domain.enums.SystemRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long>{
    @Query(value = "select * from cars_rent.roles inner join cars_rent.l_role_user lru on roles.id = lru.role_id where lru.user_id = :userId", nativeQuery = true)
    List<Role> findRolesByUserId(@Param("userId") Long id);

    Role findByRoleName(SystemRoles systemRoles);
}
