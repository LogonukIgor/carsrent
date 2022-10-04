package by.logonuk.controller;

import by.logonuk.repository.DealRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@AllArgsConstructor
@RequestMapping("/deals")
public class DealController {

    private final DealRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id){
        long userId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("result", repository.findById(userId)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> findAllDeals(){
        return new ResponseEntity<>(Collections.singletonMap("result", repository.findAll()), HttpStatus.OK);
    }
}
