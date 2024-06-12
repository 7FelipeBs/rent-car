package com.rentcar.security.controller;

import com.rentcar.security.dto.login.LoginRequestDto;
import com.rentcar.security.dto.login.SignupRequestDto;
import com.rentcar.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody LoginRequestDto loginRequest) {
        return authService.signin(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto signUpRequest) {
        return authService.signup(signUpRequest);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser(@RequestBody String refreshToken) {
        return authService.logoutUser(refreshToken);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody String refreshToken) {
        return authService.refreshtoken(refreshToken);
    }
}
