package by.logonuk.controller;

import by.logonuk.controller.requests.CarCreateRequest;
import by.logonuk.controller.requests.DrivingLicenceCreateRequest;
import by.logonuk.controller.requests.DrivingLicenceResponse;
import by.logonuk.controller.requests.DrivingLicenceUpdateRequest;
import by.logonuk.domain.DrivingLicence;
import by.logonuk.domain.User;
import by.logonuk.repository.DrivingLicenceRepository;
import by.logonuk.service.LicenceService;
import by.logonuk.validation.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/licence")
@RequiredArgsConstructor
public class DrivingLicenceController {

    private final LicenceService licenceService;

    private final ConversionService convertor;

    private static final String RESULT = "result";

    private final CustomValidator validator;

    @PostMapping
    public ResponseEntity<Object> createLicence(@Valid @RequestBody DrivingLicenceCreateRequest drivingLicenceCreateRequest){
        User user = licenceService.validateLicenceForUser(drivingLicenceCreateRequest.getUserId());
        validator.validLicenceDate(drivingLicenceCreateRequest.getDateOfIssue(), drivingLicenceCreateRequest.getValidUntil());
        DrivingLicence drivingLicence = convertor.convert(drivingLicenceCreateRequest, DrivingLicence.class);
        DrivingLicence savedDrivingLicence = licenceService.createLicence(user, drivingLicence);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, convertor.convert(savedDrivingLicence, DrivingLicenceResponse.class)), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Object> updateLicence(@Valid @RequestBody DrivingLicenceUpdateRequest drivingLicenceUpdateRequest){
        User user = licenceService.validateLicenceForUserUpdate(drivingLicenceUpdateRequest.getUserId());
        validator.validLicenceDate(drivingLicenceUpdateRequest.getDateOfIssue(), drivingLicenceUpdateRequest.getValidUntil());
        DrivingLicence drivingLicence = convertor.convert(drivingLicenceUpdateRequest, DrivingLicence.class);
        DrivingLicence savedDrivingLicence = licenceService.updateLicence(user, drivingLicence);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, convertor.convert(savedDrivingLicence, DrivingLicenceResponse.class)), HttpStatus.CREATED);
    }

//    @DeleteMapping
//    public ResponseEntity<Object> updateLicence(@Valid @RequestBody DrivingLicenceUpdateRequest drivingLicenceUpdateRequest){
//        User user = licenceService.validateLicenceForUserUpdate(drivingLicenceUpdateRequest.getUserId());
//        validator.validLicenceDate(drivingLicenceUpdateRequest.getDateOfIssue(), drivingLicenceUpdateRequest.getValidUntil());
//        DrivingLicence drivingLicence = convertor.convert(drivingLicenceUpdateRequest, DrivingLicence.class);
//        DrivingLicence savedDrivingLicence = licenceService.updateLicence(user, drivingLicence);
//        return new ResponseEntity<>(Collections.singletonMap(RESULT, convertor.convert(savedDrivingLicence, DrivingLicenceResponse.class)), HttpStatus.CREATED);
//    }
}
