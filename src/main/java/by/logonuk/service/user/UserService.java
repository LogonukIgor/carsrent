package by.logonuk.service.user;

import by.logonuk.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    Optional<User> findOne(Long id);

    List<User> findAll(int limit, int offset);

    User create(User object);

    User update(User object);

    Long delete(Long id);
}
