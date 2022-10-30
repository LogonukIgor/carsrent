package by.logonuk.controller;

import by.logonuk.controller.requests.auth.AuthRequest;
import by.logonuk.controller.responses.auth.AuthResponse;
import by.logonuk.security.jwt.JwtTokenHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth controller")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenHelper tokenUtils;

    private final UserDetailsService userProvider;

    @PostMapping
    @Operation(summary = "Login",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AuthResponse.class))))
            })
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest request) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return ResponseEntity.ok(
                AuthResponse
                        .builder()
                        .username(request.getLogin())
                        .token(tokenUtils.generateToken(userProvider.loadUserByUsername(request.getLogin())))
                        .build()
        );
    }
}
