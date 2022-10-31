package by.logonuk.controller;

import by.logonuk.controller.requests.user.UserCreateRequest;
import by.logonuk.controller.requests.user.UserUpdateRequest;
import by.logonuk.controller.responses.ResponseMapper;
import by.logonuk.controller.responses.user.UserResponse;
import by.logonuk.domain.Deal;
import by.logonuk.domain.User;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.UserRepository;
import by.logonuk.security.util.PrincipalUtil;
import by.logonuk.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
@Tag(name = "User controller")
@Transactional
public class UserController {

    private final UserRepository repository;

    private final UserService userService;

    private final ConversionService converter;

    private final ResponseMapper userResponseMapper;

    private static final String RESULT = "result";

    private static final String USER_NOT_FOUND = "User with this %s = %s does not exist";

    @GetMapping()
    @Secured({"ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Get user info",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                            schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found user",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
            })
    public ResponseEntity<Object> findById(@ApiIgnore Principal principal) {

        String login = PrincipalUtil.getUsername(principal);
        Optional<User> searchUser = repository.findByCredentialsLoginAndTechnicalInfoIsDeleted(login, false);

        User user = searchUser.orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND.formatted("login", login)));

        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(user)), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Get page of users",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                            schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found users",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
            })
    public ResponseEntity<Object> findAllUsers(@ApiIgnore Principal principal, @ParameterObject Pageable pageable) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT,
                repository.findAll(pageable)
                        .stream()
                        .filter(i -> !i.getTechnicalInfo().getIsDeleted())
                        .map(userResponseMapper::mapUserResponse)
                        .toList()), HttpStatus.OK);
    }

    @GetMapping("/function")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Get number of registered users", parameters = {
            @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))})
    public ResponseEntity<Object> getNumberOfUsers(@ApiIgnore Principal principal) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.numberOfUsers(false)), HttpStatus.OK);
    }

    @GetMapping("/activation")
    @Operation(summary = "Activate user mail", description = "Activate mail - return user (+ROLE_USER)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully validate user mail",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
            })
    public ResponseEntity<Object> activationUserMail(@RequestParam("code") String code) {

        Optional<User> searchResult = repository.findByActivationCodeAndTechnicalInfoIsDeleted(code, false);
        User user = searchResult.orElseThrow(() -> new NoSuchEntityException("User with this activation code = " + code + " does not exist activation code"));

        User savedUser = userService.updateUserToUserRole(user);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(savedUser)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create user", description = "Create a new user - return new user.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully create user",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
            })
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        User user = converter.convert(userCreateRequest, User.class);
        User savedUser = userService.createUserWithAnonymousRole(user);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(savedUser)), HttpStatus.CREATED);
    }

    @PutMapping
    @Secured({"ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Update user",
            description = "Create a new user - return new user.",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully update user",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
            })
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest, @ApiIgnore Principal principal) {

        User user = converter.convert(userUpdateRequest, User.class);
        User updatedUser = userService.updateUser(user);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(updatedUser)), HttpStatus.OK);
    }

    @PatchMapping
    @Secured({"ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Delete user profile(soft)", description = "Delete user - return deleted user info",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully delete user",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
            })
    public ResponseEntity<Object> softUserDelete(@ApiIgnore Principal principal) {

        String login = PrincipalUtil.getUsername(principal);
        Optional<User> searchUser = repository.findByCredentialsLoginAndTechnicalInfoIsDeleted(login, false);
        User user = searchUser.orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND.formatted("id", login)));

        Deal deal = user.getDeal();
        if(deal!=null){
            throw new NoSuchEntityException("User has an open case, so deletion is not possible");
        }
        user.getTechnicalInfo().setIsDeleted(true);


        repository.save(user);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, userResponseMapper.mapUserResponse(user)), HttpStatus.OK);
    }
}
