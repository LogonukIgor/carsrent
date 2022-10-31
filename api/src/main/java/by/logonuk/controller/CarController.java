package by.logonuk.controller;

import by.logonuk.controller.converters.car.request.CarCreateMapper;
import by.logonuk.controller.converters.car.request.CarUpdateMapper;
import by.logonuk.controller.requests.car.CarCreateRequest;
import by.logonuk.controller.requests.car.CarUpdateRequest;
import by.logonuk.controller.responses.car.CarResponse;
import by.logonuk.domain.Car;
import by.logonuk.domain.Deal;
import by.logonuk.domain.User;
import by.logonuk.domain.attachments.TechnicalInfo;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.CarRepository;
import by.logonuk.repository.UserRepository;
import by.logonuk.security.util.PrincipalUtil;
import by.logonuk.service.CarService;
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
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
@Tag(name = "Car controller")
@Transactional
public class CarController {

    private final CarRepository repository;

    private final UserRepository userRepository;

    private final CarService carService;

    private final ConversionService converter;

    private static final String RESULT = "result";

    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Get car",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Find car",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CarResponse.class))))
            })
    public ResponseEntity<Object> findById(@ApiIgnore Principal principal) {
        String login = PrincipalUtil.getUsername(principal);
        Optional<User> searchUser = userRepository.findByCredentialsLoginAndTechnicalInfoIsDeleted(login, false);

        Deal deal = searchUser.get().getDeal();
        if (deal == null) {
            throw new NoSuchEntityException("User does not have a deal");
        }
        Car car = deal.getCar();
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(car, CarResponse.class)), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all cars",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Find cars",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CarResponse.class))))
            })
    public ResponseEntity<Object> findAllCars(@ParameterObject Pageable pageable) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT,
                repository.findAll(pageable)
                        .stream()
                        .filter(j -> j.getIsInStock() != null && !j.getTechnicalInfo().getIsDeleted())
                        .map(i -> converter.convert(i, CarResponse.class)).collect(Collectors.toList()))
                , HttpStatus.OK);
    }

    @GetMapping("/function")
    @Operation(summary = "Get number of cars in stock")
    public ResponseEntity<Object> getNumberOfCarsInStock() {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.carsInStock()), HttpStatus.OK);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Create car", description = "Create car - return created car",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully created car",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CarResponse.class))))
            })
    public ResponseEntity<Object> createCar(@Valid @RequestBody CarCreateRequest carCreateRequest, @ApiIgnore Principal principal) {

        Timestamp timestamp = new Timestamp(new Date().getTime());
        TechnicalInfo technicalInfo = new TechnicalInfo(timestamp, timestamp, false);

        CarCreateMapper carCreateMapper = new CarCreateMapper(carCreateRequest, technicalInfo);

        Car car = carService.createCar(carCreateMapper.carMapping(), carCreateMapper.modelMapping(), carCreateMapper.classificationMapping(), carCreateMapper.carManufactureMapping());

        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(car, CarResponse.class)), HttpStatus.CREATED);
    }

    @PutMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Update car", description = "Update car - return updated car",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated car",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CarResponse.class))))
            })
    public ResponseEntity<Object> updateCar(@Valid @RequestBody CarUpdateRequest carUpdateRequest, @ApiIgnore Principal principal) {

        CarUpdateMapper carCreateMapping = new CarUpdateMapper(repository, carUpdateRequest, new Timestamp((new Date().getTime())));

        Car updatedCar = carService.updateCar(carCreateMapping.carMapping(), carCreateMapping.modelMapping(), carCreateMapping.classificationMapping(), carCreateMapping.carManufactureMapping());

        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(updatedCar, CarResponse.class)), HttpStatus.OK);
    }

    @PatchMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Delete car(soft)", description = "Delete car - return deleted car",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully delete car",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CarResponse.class))))
            })
    public ResponseEntity<Object> softCarDelete(@ApiIgnore Principal principal) {

        Optional<User> searchUser = userRepository.findByCredentialsLoginAndTechnicalInfoIsDeleted(PrincipalUtil.getUsername(principal), false);

        Deal deal = searchUser.get().getDeal();
        if (deal == null) {
            throw new NoSuchEntityException("User does not have a deal");
        }

        Car car = deal.getCar();
        car.getTechnicalInfo().setIsDeleted(true);
        repository.save(car);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(car, CarResponse.class)), HttpStatus.OK);
    }
}
