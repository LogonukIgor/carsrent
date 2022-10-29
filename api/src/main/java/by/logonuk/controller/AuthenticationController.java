package by.logonuk.controller;

import by.logonuk.controller.auth.AuthRequest;
import by.logonuk.controller.auth.AuthResponse;
import by.logonuk.security.jwt.JwtTokenHelper;
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
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenHelper tokenUtils;

    private final UserDetailsService userProvider;

    @PostMapping
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
