package by.logonuk.controller;

import by.logonuk.repository.ModelRepository;
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
@RequestMapping("/models")
public class ModelController {
    private final ModelRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id){
        int modelId = Integer.getInteger(id);

        return new ResponseEntity<>(Collections.singletonMap("result", repository.findById(modelId)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> findAllModels(){
        return new ResponseEntity<>(Collections.singletonMap("result", repository.findAll()), HttpStatus.OK);
    }
}
