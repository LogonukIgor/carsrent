package by.logonuk.controller;

import by.logonuk.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    public final CarRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id){
        long userId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("result", repository.findById(userId)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> findAllUsers(){
        return new ResponseEntity<>(Collections.singletonMap("result", repository.findAll()), HttpStatus.OK);
    }
}
