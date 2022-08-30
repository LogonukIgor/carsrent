package by.logonuk.service.user;

import by.logonuk.domain.User;
import by.logonuk.repository.user.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryInterface userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return userRepository.findAll(limit, offset);
    }

    @Override
    public User create(User object) {
        return userRepository.create(object);
    }

    @Override
    public User update(User object) {
        return userRepository.update(object);
    }

    @Override
    public Long delete(Long id) {
        return userRepository.delete(id);
    }

    @Override
    public Map<String, Integer> numOfUsers() {
        return userRepository.numOfUsers();
    }

}
