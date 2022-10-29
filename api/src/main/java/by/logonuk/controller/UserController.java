package by.logonuk.controller;

import by.logonuk.controller.requests.UserCreateRequest;
import by.logonuk.controller.requests.UserUpdateRequest;
import by.logonuk.controller.responses.ResponseMapper;
import by.logonuk.domain.User;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.UserRepository;
import by.logonuk.security.util.PrincipalUtil;
import by.logonuk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Transactional
public class UserController {

    private final UserRepository repository;

    private final UserService userService;

    private final ConversionService converter;

    private final ResponseMapper userResponseMapper;

    private static final String RESULT = "result";

    private static final String USER_NOT_FOUND = "User with this %s = %s does not exist";

    @GetMapping()
    public ResponseEntity<Object> findById(@ApiIgnore Principal principal) {

        String login = PrincipalUtil.getUsername(principal);
        Optional<User> searchUser = repository.findByCredentialsLoginAndTechnicalInfoIsDeleted(login, false);

        User user = searchUser.orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND.formatted("login", login)));

        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(user)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllUsers(@ApiIgnore Principal principal) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT,
                repository.findAllByTechnicalInfoIsDeleted(false).stream()
                        .map(userResponseMapper::mapUserResponse)
                        .toList()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        User user = converter.convert(userCreateRequest, User.class);
        User savedUser = userService.createUserWithAnonymousRole(user);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(savedUser)), HttpStatus.CREATED);
    }

    @GetMapping("/activation")
    public ResponseEntity<Object> activationUserMail(@RequestParam("code") String code) {

        Optional<User> searchResult = repository.findByActivationCodeAndTechnicalInfoIsDeleted(code, false);
        User user = searchResult.orElseThrow(() -> new NoSuchEntityException("User with this activation code = " + code + " does not exist activation code"));

        userService.updateUserToUserRole(user);
        User savedUser = userService.updateUserToUserRole(user);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(savedUser)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest, @ApiIgnore Principal principal) {

        User user = converter.convert(userUpdateRequest, User.class);
        User updatedUser = userService.updateUser(user);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(updatedUser)), HttpStatus.CREATED);
    }

    @PatchMapping("/delete")
    public ResponseEntity<Object> softUserDelete(@ApiIgnore Principal principal) {

        String login = PrincipalUtil.getUsername(principal);
        Optional<User> searchUser = repository.findByCredentialsLoginAndTechnicalInfoIsDeleted(login, false);
        User user = searchUser.orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND.formatted("id", login)));

        user.getTechnicalInfo().setIsDeleted(true);

        repository.save(user);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(user)), HttpStatus.OK);
    }

    @GetMapping("/function")
    public ResponseEntity<Object> getNumberOfUsers(@ApiIgnore Principal principal) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.numberOfUsers(false)), HttpStatus.OK);
    }
}
