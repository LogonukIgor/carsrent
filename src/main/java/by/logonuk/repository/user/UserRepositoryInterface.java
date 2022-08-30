package by.logonuk.repository.user;

import by.logonuk.domain.User;
import by.logonuk.repository.CRUDRepository;

import java.util.Map;

public interface UserRepositoryInterface extends CRUDRepository<Long, User> {

    Map<String, Integer> numOfUsers();
}
