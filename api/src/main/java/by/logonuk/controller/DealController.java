package by.logonuk.controller;

import by.logonuk.controller.requests.DealCreateRequest;
import by.logonuk.controller.requests.DealUpdateRequest;
import by.logonuk.controller.responses.DealResponse;
import by.logonuk.controller.responses.UserResponse;
import by.logonuk.domain.Car;
import by.logonuk.domain.Deal;
import by.logonuk.domain.User;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.DealRepository;
import by.logonuk.service.DealService;
import by.logonuk.validation.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deals")
public class DealController {

    private final DealRepository repository;

    private final DealService dealService;

    private final ConversionService converter;

    private final CustomValidator validator;

    private static final String RESULT = "result";

    private static final String DEAL_NOT_FOUND = "Deal with this %s = %d does not exist";

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        long dealId = Long.parseLong(id);
        Optional<Deal> searchDeal = repository.findByIdAndTechnicalInfoIsDeleted(dealId, false);
        Deal deal = searchDeal.orElseThrow(() -> new NoSuchEntityException(DEAL_NOT_FOUND.formatted("id", dealId)));
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(deal, DealResponse.class)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAllDeals() {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.findAllByTechnicalInfoIsDeleted(false).stream().map(i -> converter.convert(i, DealResponse.class)).collect(Collectors.toList())), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createDeal(@Valid @RequestBody DealCreateRequest dealCreateRequest) {
        Map<String, Object> entityMap = dealService.validateDeal(dealCreateRequest.getUserId(), dealCreateRequest.getCarId());
        Deal deal = converter.convert(dealCreateRequest, Deal.class);
        validator.validDealDate(deal.getReceivingDate(), deal.getReturnDate());
        Deal savedDeal = dealService.createDeal((User) entityMap.get("user"), (Car) entityMap.get("car"), deal);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(savedDeal, DealResponse.class)), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Object> updateCar(@Valid @RequestBody DealUpdateRequest dealUpdateRequest) {
        Map<String, Object> entityMap = dealService.validateDeal(dealUpdateRequest.getUserId(), dealUpdateRequest.getCarId());
        Deal deal = converter.convert(dealUpdateRequest, Deal.class);
        validator.validDealDate(deal.getReceivingDate(), deal.getReturnDate());
        Deal updateDeal = dealService.updateDeal((User) entityMap.get("user"), (Car) entityMap.get("car"), deal);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(updateDeal, DealResponse.class)), HttpStatus.CREATED);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Object> softDealDelete(@PathVariable String id) {
        long dealId = Long.parseLong(id);
        Optional<Deal> searchUser = repository.findByIdAndTechnicalInfoIsDeleted(dealId, false);
        Deal deal = searchUser.orElseThrow(() -> new NoSuchEntityException(DEAL_NOT_FOUND.formatted("id", dealId)));
        deal.getTechnicalInfo().setIsDeleted(true);
        repository.save(deal);
        return new ResponseEntity<>(Collections.singletonMap(RESULT, converter.convert(deal, UserResponse.class)), HttpStatus.OK);
    }
}
