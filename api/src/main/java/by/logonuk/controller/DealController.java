package by.logonuk.controller;

import by.logonuk.controller.requests.DealCreateRequest;
import by.logonuk.domain.Car;
import by.logonuk.domain.User;
import by.logonuk.repository.DealRepository;
import by.logonuk.service.DealService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deals")
public class DealController {

    private final DealRepository repository;

    private final DealService dealService;

    private static final String RESULT = "result";

//    @GetMapping("/{id}")
//    public ResponseEntity<Object> findById(@PathVariable String id) {
//        long userId = Long.parseLong(id);
//
//        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.findById(userId)), HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<Object> findAllDeals() {
//        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.findAll()), HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<Object> createDeal(@RequestBody DealCreateRequest dealCreateRequest){
        Map<String, Object> entityMap = dealService.validateDeal(dealCreateRequest.getUserId(), dealCreateRequest.getCarId());
        User user = (User) entityMap.get("user");
        Car car = (Car) entityMap.get("car");

        return new ResponseEntity<>(Collections.singletonMap(RESULT, "eac"), HttpStatus.CREATED);
    }
}
