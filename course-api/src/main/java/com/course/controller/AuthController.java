package com.course.controller;

import com.course.dto.LoginRequest;
import com.course.dto.NavigationModeRequest;
import com.course.dto.RegisterRequest;
import com.course.dto.TokenResponse;
import com.course.dto.UserResponse;
import com.course.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/auth/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal String username) {
        return UserResponse.from(authService.load(username));
    }

    @PutMapping("/me/mode")
    public UserResponse changeMode(@AuthenticationPrincipal String username, @Valid @RequestBody NavigationModeRequest request) {
        return authService.changeNavigationMode(username, request.mode());
    }
}
