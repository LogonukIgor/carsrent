package by.logonuk.controller;

import by.logonuk.controller.requests.UserCreateRequest;
import by.logonuk.controller.requests.UserUpdateRequest;
import by.logonuk.controller.responses.UserResponse;
import by.logonuk.domain.User;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.RoleRepository;
import by.logonuk.repository.UserRepository;
import by.logonuk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    private final UserService userService;

    private final ConversionService converter;

    private static final String RESULT = "result";

    private static final String USER_NOT_FOUND = "User with this %s = %d does not exist";

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        long userId = Long.parseLong(id);
        Optional<User> searchUser = repository.findByIdAndTechnicalInfoIsDeleted(userId, false);
        User user = searchUser.orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND.formatted("id", userId)));
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(user, UserResponse.class)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAllUsers() {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.findAllByTechnicalInfoIsDeleted(false).stream().map(i -> converter.convert(i, UserResponse.class)).collect(Collectors.toList())), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        User user = converter.convert(userCreateRequest, User.class);
        User savedUser = userService.createUserWithAnonymousRole(user);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(savedUser, UserResponse.class)), HttpStatus.CREATED);
    }

    @GetMapping("/activation")
    @Transactional
    public ResponseEntity<Object> activationUserMail(@RequestParam("code") String code) {
        Optional<User> searchResult = repository.findByActivationCodeAndTechnicalInfoIsDeleted(code, false);
        User user = searchResult.orElseThrow(() -> new NoSuchEntityException("User with this activation code = " + code + " does not exist activation code"));
        userService.updateUserToUserRole(user);
        User savedUser = userService.updateUserToUserRole(user);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(savedUser, UserResponse.class)), HttpStatus.OK);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        User user = converter.convert(userUpdateRequest, User.class);
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(updatedUser, UserResponse.class)), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
        long userId = Long.parseLong(id);
        Optional<User> searchUser = repository.findByIdAndTechnicalInfoIsDeleted(userId, false);
        User user = searchUser.orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND.formatted("id", userId)));
        user.getTechnicalInfo().setIsDeleted(true);
        repository.save(user);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(user, UserResponse.class)), HttpStatus.OK);
    }

//    @DeleteMapping("/delete/hard/{id}")
//    public ResponseEntity<Object> adminDeleteUser(@PathVariable String id) {
//        long userId = Long.parseLong(id);
//        Optional<User> searchUser = repository.findById(userId);
//        User user = searchUser.orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND.formatted("id", userId)));
//        user.setRoles(new HashSet<>());
//        repository.customUserDelete(userId);
//        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(user, UserResponse.class)), HttpStatus.OK);
//    }
}
