package by.logonuk.controller;

import by.logonuk.controller.requests.DrivingLicenceCreateRequest;
import by.logonuk.controller.responses.DrivingLicenceResponse;
import by.logonuk.controller.requests.DrivingLicenceUpdateRequest;
import by.logonuk.domain.DrivingLicence;
import by.logonuk.domain.User;
import by.logonuk.exception.CreateLicenceForUserException;
import by.logonuk.repository.DrivingLicenceRepository;
import by.logonuk.repository.UserRepository;
import by.logonuk.security.util.PrincipalUtil;
import by.logonuk.service.LicenceService;
import by.logonuk.validation.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/licence")
@RequiredArgsConstructor
@Transactional
public class DrivingLicenceController {

    private final DrivingLicenceRepository repository;

    private final LicenceService licenceService;

    private final UserRepository userRepository;

    private final ConversionService converter;

    private final CustomValidator validator;

    private static final String RESULT = "result";

    private static final String LICENCE_NOT_FOUND = "Licence with this %s = %d does not exist";

    @GetMapping()
    @Secured({"ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> findById(@ApiIgnore Principal principal) {

        String login = PrincipalUtil.getUsername(principal);
        Optional<User> searchUser = userRepository.findByCredentialsLoginAndTechnicalInfoIsDeleted(login, false);

        DrivingLicence licence = searchUser.get().getDrivingLicence();

        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(licence, DrivingLicenceResponse.class)), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> findAllUsers(@ApiIgnore Principal principal) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT,
                repository.findAll().stream()
                        .map(x->converter.convert(x, DrivingLicenceResponse.class))
                        .toList()), HttpStatus.OK);
    }


    @PostMapping
    @Secured({"ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> createLicence(@Valid @RequestBody DrivingLicenceCreateRequest drivingLicenceCreateRequest, @ApiIgnore Principal principal){

        User user = licenceService.validateLicenceForUser(drivingLicenceCreateRequest.getUserId());

        validator.validLicenceDate(drivingLicenceCreateRequest.getDateOfIssue(), drivingLicenceCreateRequest.getValidUntil());

        DrivingLicence drivingLicence = converter.convert(drivingLicenceCreateRequest, DrivingLicence.class);
        DrivingLicence savedDrivingLicence = licenceService.createLicence(user, drivingLicence);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(savedDrivingLicence, DrivingLicenceResponse.class)), HttpStatus.CREATED);
    }

    @PutMapping
    @Secured({"ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> updateLicence(@Valid @RequestBody DrivingLicenceUpdateRequest drivingLicenceUpdateRequest, @ApiIgnore Principal principal){

        User user = licenceService.validateLicenceForUserUpdate(drivingLicenceUpdateRequest.getUserId());

        validator.validLicenceDate(drivingLicenceUpdateRequest.getDateOfIssue(), drivingLicenceUpdateRequest.getValidUntil());

        DrivingLicence drivingLicence = converter.convert(drivingLicenceUpdateRequest, DrivingLicence.class);
        DrivingLicence savedDrivingLicence = licenceService.updateLicence(user, drivingLicence);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(savedDrivingLicence, DrivingLicenceResponse.class)), HttpStatus.CREATED);
    }

    @DeleteMapping
    @Secured({"ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> deleteLicence(@ApiIgnore Principal principal) {

        String login = PrincipalUtil.getUsername(principal);
        Optional<User> searchUser = userRepository.findByCredentialsLoginAndTechnicalInfoIsDeleted(login, false);

        User user = searchUser.get();

        if(user.getDrivingLicence() == null){
            throw new CreateLicenceForUserException("The user with id = " + user.getId() + " does not has a license");
        }

        DrivingLicence licence = searchUser.get().getDrivingLicence();
        repository.delete(licence);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(licence, DrivingLicenceResponse.class)), HttpStatus.OK);
    }
}

