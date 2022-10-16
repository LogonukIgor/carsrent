package by.logonuk.controller;

import by.logonuk.controller.requests.UserCreateRequest;
import by.logonuk.domain.User;
import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import by.logonuk.domain.embed.user.Credentials;
import by.logonuk.domain.enums.SystemRoles;
import by.logonuk.repository.UserRepository;
import by.logonuk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id){
        long userId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("result", repository.findById(userId)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> findAllUsers(){
        return new ResponseEntity<>(Collections.singletonMap("result", repository.findAll()), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> createUser(@RequestBody UserCreateRequest userCreateRequest){

        Timestamp timestamp = new Timestamp(new Date().getTime());

        User user = new User();
        user.setUserName(userCreateRequest.getUserName());
        user.setSurname(userCreateRequest.getSurname());

        Credentials credentials = new Credentials(
            userCreateRequest.getLogin(),
            userCreateRequest.getMail(),
            userCreateRequest.getPassword()
        );

        TechnicalDatesAndInfo techDateInf = new TechnicalDatesAndInfo(timestamp, timestamp, false);

        user.setCredentials(credentials);
        user.setTechnicalDatesAndInfo(techDateInf);
        user.setIsMailActivated(false);

        User savedUser = userService.createUserWithAnonymousRole(user);
        return new ResponseEntity<>(Collections.singletonMap("result", userService.responseUserWithRoles(savedUser, SystemRoles.ROLE_ANONYMOUS)), HttpStatus.CREATED);
//        return new ResponseEntity<>(Collections.singletonMap("result", "Successful user addition. Check your mail"), HttpStatus.CREATED);
    }

    @GetMapping ("/activation/{code}")
    public ResponseEntity<Object> activationUserMail(@PathVariable String code){
        Optional<User> searchResult = repository.findByActivationCode(code);
        if(searchResult.isPresent()){
            User user = searchResult.get();
            userService.updateUserToUserRole(user);
            return new ResponseEntity<>(Collections.singletonMap("result", "Mail successfully confirmed"), HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.singletonMap("result", "User with this code does not exist"), HttpStatus.NOT_FOUND);
    }
}
