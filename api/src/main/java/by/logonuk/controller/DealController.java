package by.logonuk.controller;

import by.logonuk.controller.requests.archive.ArchiveRequest;
import by.logonuk.controller.requests.deal.DealCreateRequest;
import by.logonuk.controller.requests.deal.DealUpdateRequest;
import by.logonuk.controller.responses.ResponseMapper;
import by.logonuk.controller.responses.deal.DealResponse;
import by.logonuk.domain.Car;
import by.logonuk.domain.Deal;
import by.logonuk.domain.User;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.DealRepository;
import by.logonuk.repository.UserRepository;
import by.logonuk.security.util.PrincipalUtil;
import by.logonuk.service.DealService;
import by.logonuk.validation.CustomValidator;
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
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deals")
@Tag(name = "Deal controller")
@Transactional
public class DealController {

    private final DealRepository repository;

    private final UserRepository userRepository;

    private final DealService dealService;

    private final ConversionService converter;

    private final ResponseMapper responseMapper;

    private final CustomValidator validator;

    private static final String RESULT = "result";

    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Get deal",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Find deal",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DealResponse.class))))
            })
    public ResponseEntity<Object> findById(Principal principal) {
        String login = PrincipalUtil.getUsername(principal);
        Optional<User> searchUser = userRepository.findByCredentialsLoginAndTechnicalInfoIsDeleted(login, false);

        Deal deal = searchUser.get().getDeal();
        if (deal == null) {
            throw new NoSuchEntityException("User does not have a deal");
        }
        return new ResponseEntity<>(Collections.singletonMap(RESULT, responseMapper.mapDealResponse(deal)), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Get all deals",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Find deals",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DealResponse.class))))
            })
    public ResponseEntity<Object> findAllDeals(@ApiIgnore Principal principal, @ParameterObject Pageable pageable) {

        return new ResponseEntity<>(Collections.singletonMap(RESULT,
                repository.findAll(pageable)
                        .stream()
                        .filter(i->!i.getTechnicalInfo().getIsDeleted())
                        .map(responseMapper::mapDealResponse).toList())
                , HttpStatus.OK);
    }

    @GetMapping("/function")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Get number of open deals", parameters = {
            @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))
    })
    public ResponseEntity<Object> getNumberOfDeals(@ApiIgnore Principal principal) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.CountOfDeals()), HttpStatus.OK);
    }

    @PostMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Create deal", description = "Create deal - return created deal.",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully create deal",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DealResponse.class))))
            })
    public ResponseEntity<Object> createDeal(@Valid @RequestBody DealCreateRequest dealCreateRequest, @ApiIgnore Principal principal) {

        Map<String, Object> entityMap = dealService.validateDeal(dealCreateRequest.getUserId(), dealCreateRequest.getCarId());
        Deal deal = converter.convert(dealCreateRequest, Deal.class);
        validator.validDealDate(deal.getReceivingDate(), deal.getReturnDate());

        Deal savedDeal = dealService.createDeal((User) entityMap.get("user"), (Car) entityMap.get("car"), deal);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, responseMapper.mapDealResponse(savedDeal)), HttpStatus.CREATED);
    }

    @PutMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Update deal", description = "Update deal - return updated deal.",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully update deal",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DealResponse.class))))
            })
    public ResponseEntity<Object> updateDeal(@Valid @RequestBody DealUpdateRequest dealUpdateRequest, @ApiIgnore Principal principal) {

        Map<String, Object> entityMap = dealService.validateDeal(dealUpdateRequest.getUserId(), dealUpdateRequest.getCarId());
        Deal deal = converter.convert(dealUpdateRequest, Deal.class);
        validator.validDealDate(deal.getReceivingDate(), deal.getReturnDate());

        Deal updateDeal = dealService.updateDeal((User) entityMap.get("user"), (Car) entityMap.get("car"), deal);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, responseMapper.mapDealResponse(updateDeal)), HttpStatus.OK);
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @Operation(summary = "Delete deal", description = "Delete deal - return deleted deal.",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully delete deal",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DealResponse.class))))
            })
    public ResponseEntity<Object> softDeleteDeal(@RequestBody ArchiveRequest archiveRequest, @ApiIgnore Principal principal) {
        Long userId = Long.parseLong(archiveRequest.getUserId());
        Optional<User> searchUser = userRepository.findByIdAndTechnicalInfoIsDeleted(userId, false);

        User user = searchUser.orElseThrow(() -> new NoSuchEntityException("User with id = " + userId + " does not exist"));
        Deal deal = user.getDeal();

        if (deal == null) {
            throw new NoSuchEntityException("User does not have a deal");
        }

        dealService.deleteDeal(deal, user, archiveRequest.getIsSuccessfully());

        return new ResponseEntity<>(Collections.singletonMap(RESULT, responseMapper.mapDealResponse(deal)), HttpStatus.OK);
    }
}
