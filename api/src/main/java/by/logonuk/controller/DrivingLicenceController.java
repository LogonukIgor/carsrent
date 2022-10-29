package by.logonuk.controller;

import by.logonuk.controller.requests.DrivingLicenceCreateRequest;
import by.logonuk.controller.responses.DrivingLicenceResponse;
import by.logonuk.controller.requests.DrivingLicenceUpdateRequest;
import by.logonuk.domain.DrivingLicence;
import by.logonuk.domain.User;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.DrivingLicenceRepository;
import by.logonuk.service.LicenceService;
import by.logonuk.validation.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/licence")
@RequiredArgsConstructor
@Transactional
public class DrivingLicenceController {

    private final DrivingLicenceRepository repository;

    private final LicenceService licenceService;

    private final ConversionService converter;

    private final CustomValidator validator;

    private static final String RESULT = "result";

    private static final String LICENCE_NOT_FOUND = "Licence with this %s = %d does not exist";

    @PostMapping
    public ResponseEntity<Object> createLicence(@Valid @RequestBody DrivingLicenceCreateRequest drivingLicenceCreateRequest){
        User user = licenceService.validateLicenceForUser(drivingLicenceCreateRequest.getUserId());
        validator.validLicenceDate(drivingLicenceCreateRequest.getDateOfIssue(), drivingLicenceCreateRequest.getValidUntil());
        DrivingLicence drivingLicence = converter.convert(drivingLicenceCreateRequest, DrivingLicence.class);
        DrivingLicence savedDrivingLicence = licenceService.createLicence(user, drivingLicence);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(savedDrivingLicence, DrivingLicenceResponse.class)), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Object> updateLicence(@Valid @RequestBody DrivingLicenceUpdateRequest drivingLicenceUpdateRequest){
        User user = licenceService.validateLicenceForUserUpdate(drivingLicenceUpdateRequest.getUserId());
        validator.validLicenceDate(drivingLicenceUpdateRequest.getDateOfIssue(), drivingLicenceUpdateRequest.getValidUntil());
        DrivingLicence drivingLicence = converter.convert(drivingLicenceUpdateRequest, DrivingLicence.class);
        DrivingLicence savedDrivingLicence = licenceService.updateLicence(user, drivingLicence);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(savedDrivingLicence, DrivingLicenceResponse.class)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> softLicenceDelete(@PathVariable String id) {
        long licenceId = Long.parseLong(id);
        Optional<DrivingLicence> searchLicence = repository.findByIdAndTechnicalInfoIsDeleted(licenceId, false);
        DrivingLicence drivingLicence = searchLicence.orElseThrow(() -> new NoSuchEntityException(LICENCE_NOT_FOUND.formatted("id", licenceId)));
        drivingLicence.getTechnicalInfo().setIsDeleted(true);
        repository.save(drivingLicence);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(drivingLicence, DrivingLicenceResponse.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLicence(@PathVariable String id) {
        long licenceId = Long.parseLong(id);
        Optional<DrivingLicence> searchLicence = repository.findById(licenceId);
        DrivingLicence drivingLicence = searchLicence.orElseThrow(() -> new NoSuchEntityException(LICENCE_NOT_FOUND.formatted("id", licenceId)));
        repository.delete(drivingLicence);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(drivingLicence, DrivingLicenceResponse.class)), HttpStatus.OK);
    }
}

