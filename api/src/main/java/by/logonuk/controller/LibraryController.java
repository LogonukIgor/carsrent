package by.logonuk.controller;

import by.logonuk.repository.LibraryRepository;
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
@RequestMapping("/library")
public class LibraryController {

    private final LibraryRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id){
        long libraryId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("result", repository.findById(libraryId)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> findAllFields(){
        return new ResponseEntity<>(Collections.singletonMap("result", repository.findAll()), HttpStatus.OK);
    }
}
