package by.logonuk.controller;

import by.logonuk.domain.Role;
import by.logonuk.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@Tag(name = "Role controller")
public class RoleController {
    private final RoleRepository repository;

    @GetMapping
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    @Operation(summary = "Get all roles",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", description = "Token",
                    schema = @Schema(defaultValue = "token", type = "string"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Find roles",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Role.class))))
            })
    public ResponseEntity<Object> findAllRoles(@ApiIgnore Principal principal) {
        return new ResponseEntity<>(Collections.singletonMap("result", repository.findAll()), HttpStatus.OK);
    }
}
