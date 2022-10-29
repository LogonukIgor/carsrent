package by.logonuk.controller;

import by.logonuk.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryController {

    private final LibraryRepository repository;

    private static final String RESULT = "result";

    @GetMapping
    public ResponseEntity<Object> findAllFields(@ApiIgnore Pageable pageable) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.findAll(pageable)), HttpStatus.OK);
    }
}
