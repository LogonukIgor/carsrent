package by.logonuk.controller;

import by.logonuk.controller.requests.ArchiveRequest;
import by.logonuk.controller.requests.DealCreateRequest;
import by.logonuk.controller.requests.DealUpdateRequest;
import by.logonuk.controller.responses.ResponseMapper;
import by.logonuk.domain.Car;
import by.logonuk.domain.Deal;
import by.logonuk.domain.User;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.DealRepository;
import by.logonuk.repository.UserRepository;
import by.logonuk.security.util.PrincipalUtil;
import by.logonuk.service.DealService;
import by.logonuk.validation.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deals")
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
    public ResponseEntity<Object> findAllDeals(@ApiIgnore Principal principal) {

        return new ResponseEntity<>(Collections.singletonMap(RESULT,
                repository.findAllByTechnicalInfoIsDeleted(false)
                        .stream()
                        .map(responseMapper::mapDealResponse).toList())
                , HttpStatus.OK);
    }

    @GetMapping("/function")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> getNumberOfDeals(@ApiIgnore Principal principal) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.CountOfDeals()), HttpStatus.OK);
    }

    @PostMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> createDeal(@Valid @RequestBody DealCreateRequest dealCreateRequest, @ApiIgnore Principal principal) {

        Map<String, Object> entityMap = dealService.validateDeal(dealCreateRequest.getUserId(), dealCreateRequest.getCarId());
        Deal deal = converter.convert(dealCreateRequest, Deal.class);
        validator.validDealDate(deal.getReceivingDate(), deal.getReturnDate());

        Deal savedDeal = dealService.createDeal((User) entityMap.get("user"), (Car) entityMap.get("car"), deal);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, responseMapper.mapDealResponse(savedDeal)), HttpStatus.CREATED);
    }

    @PutMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> updateDeal(@Valid @RequestBody DealUpdateRequest dealUpdateRequest, @ApiIgnore Principal principal) {

        Map<String, Object> entityMap = dealService.validateDeal(dealUpdateRequest.getUserId(), dealUpdateRequest.getCarId());
        Deal deal = converter.convert(dealUpdateRequest, Deal.class);
        validator.validDealDate(deal.getReceivingDate(), deal.getReturnDate());

        Deal updateDeal = dealService.updateDeal((User) entityMap.get("user"), (Car) entityMap.get("car"), deal);

        return new ResponseEntity<>(Collections.singletonMap(RESULT, responseMapper.mapDealResponse(updateDeal)), HttpStatus.CREATED);
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
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
