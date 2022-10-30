package by.logonuk.controller;

import by.logonuk.domain.Library;
import by.logonuk.repository.LibraryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
@Tag(name = "Library controller")
public class LibraryController {

    private final LibraryRepository repository;

    private static final String RESULT = "result";

    @GetMapping
    @Operation(summary = "Get library",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Find library",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Library.class))))
            })
    public ResponseEntity<Object> findAllFields(@ParameterObject Pageable pageable) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, repository.findAll(pageable)), HttpStatus.OK);
    }
}
