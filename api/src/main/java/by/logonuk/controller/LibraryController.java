package by.logonuk.controller;

import by.logonuk.domain.Library;
import by.logonuk.domain.Model;
import by.logonuk.repository.CarManufactureRepository;
import by.logonuk.repository.ClassificationRepository;
import by.logonuk.repository.LibraryRepository;
import by.logonuk.repository.ModelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    private final ModelRepository modelRepository;

    private final ClassificationRepository classificationRepository;

    private final CarManufactureRepository carManufactureRepository;

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

    @GetMapping("/models")
    @Operation(summary = "Get all models",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Find models",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Model.class))))
            })
    public ResponseEntity<Object> findAllModels(@ParameterObject Pageable pageable) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, modelRepository.findAll(pageable)), HttpStatus.OK);
    }

    @GetMapping("/classifications")
    @Operation(summary = "Get all classifications",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Find classifications",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Model.class))))
            })
    public ResponseEntity<Object> findAllClassifications(@ParameterObject Pageable pageable) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, classificationRepository.findAll(pageable)), HttpStatus.OK);
    }

    @GetMapping("/manufacturer")
    @Operation(summary = "Get all car manufacturer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Find car manufacturer",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Model.class))))
            })
    public ResponseEntity<Object> findAllManufacturer(@ParameterObject Pageable pageable) {
        return new ResponseEntity<>(Collections.singletonMap(RESULT, carManufactureRepository.findAll(pageable)), HttpStatus.OK);
    }
}
