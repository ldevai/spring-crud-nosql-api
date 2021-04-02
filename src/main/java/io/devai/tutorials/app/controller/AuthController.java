package io.devai.tutorials.app.controller;

import io.devai.tutorials.app.dto.AuthResponse;
import io.devai.tutorials.app.dto.LoginRequest;
import io.devai.tutorials.app.dto.RegisterRequest;
import io.devai.tutorials.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity index() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse user = userService.login(request);
            log.info("[login] Authenticated {}", user.getEmail());
            return ResponseEntity.ok(user);

        } catch (LoginException e) {
            log.error("[login] Error authenticating {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse user = userService.register(request);
            log.info("[register] Registered {}", user.getEmail());
            return ResponseEntity.ok(user);

        } catch (LoginException e) {
            log.error("[register] Error registering {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
