package by.logonuk.controller;

import by.logonuk.controller.requests.UserCreateRequest;
import by.logonuk.controller.requests.UserUpdateRequest;
import by.logonuk.controller.responses.ResponseMapper;
import by.logonuk.domain.User;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.UserRepository;
import by.logonuk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    private final UserService userService;

    private final ConversionService converter;

    private final ResponseMapper userResponseMapper;

    private static final String RESULT = "result";

    private static final String USER_NOT_FOUND = "User with this %s = %d does not exist";

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        long userId = Long.parseLong(id);
        Optional<User> searchUser = repository.findByIdAndTechnicalInfoIsDeleted(userId, false);
        User user = searchUser.orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND.formatted("id", userId)));
        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(user)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAllUsers() {
        return new ResponseEntity<>(Collections.singletonMap(RESULT,
                repository.findAllByTechnicalInfoIsDeleted(false).stream()
                        .map(userResponseMapper::mapUserResponse)
                        .toList()), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        User user = converter.convert(userCreateRequest, User.class);
        User savedUser = userService.createUserWithAnonymousRole(user);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(savedUser)), HttpStatus.CREATED);
    }

    @GetMapping("/activation")
    @Transactional
    public ResponseEntity<Object> activationUserMail(@RequestParam("code") String code) {
        Optional<User> searchResult = repository.findByActivationCodeAndTechnicalInfoIsDeleted(code, false);
        User user = searchResult.orElseThrow(() -> new NoSuchEntityException("User with this activation code = " + code + " does not exist activation code"));
        userService.updateUserToUserRole(user);
        User savedUser = userService.updateUserToUserRole(user);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(savedUser)), HttpStatus.OK);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        Optional<User> searchUser = repository.findByIdAndTechnicalInfoIsDeleted(Long.parseLong(userUpdateRequest.getUserId()), false);
        searchUser.orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND.formatted("id", Long.parseLong(userUpdateRequest.getUserId()))));
        User user = converter.convert(userUpdateRequest, User.class);
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(updatedUser)), HttpStatus.CREATED);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Object> softUserDelete(@PathVariable String id) {
        long userId = Long.parseLong(id);
        Optional<User> searchUser = repository.findByIdAndTechnicalInfoIsDeleted(userId, false);
        User user = searchUser.orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND.formatted("id", userId)));
        user.getTechnicalInfo().setIsDeleted(true);
        repository.save(user);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(user)), HttpStatus.OK);
    }

    @GetMapping("/function")
    public ResponseEntity<Object> getNumberOfUsers(){
        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.numberOfUsers(false)), HttpStatus.OK);
    }
}
