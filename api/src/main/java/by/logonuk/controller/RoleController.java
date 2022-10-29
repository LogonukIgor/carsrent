package by.logonuk.controller;

import by.logonuk.repository.ClassificationRepository;
import by.logonuk.repository.RoleRepository;
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
@RequestMapping("/roles")
public class RoleController {
    private final RoleRepository repository;

    private final ClassificationRepository classificationRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        long roleId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("result", repository.findById(roleId)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAllRoles() {
        System.out.println("------------roles--------");
        return new ResponseEntity<>(Collections.singletonMap("result", repository.findAll()), HttpStatus.OK);
    }

    @GetMapping("/cl")
    public ResponseEntity<Object> findAllClassifications() {
        System.out.println("------------cl----------");
        return new ResponseEntity<>(Collections.singletonMap("result", classificationRepository.findAll()), HttpStatus.OK);
    }
}
