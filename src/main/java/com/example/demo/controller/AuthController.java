package com.example.demo.controller;

import com.example.demo.model.AuthRequest;
import com.example.demo.model.UserInfo;
import com.example.demo.service.AuthService;
import com.example.demo.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authorization", description = "Endpoints related to user authentication and authorization")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "To add new user", description = "Choose ROLE_ADMIN or ROLE_USER", tags = {"Authorization"})
    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return authService.addUser(userInfo);
    }

    @Operation(summary = "To Generate JWT Token", description = "Generating The JWT Token", tags = {"Authorization"})
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticateUser(authRequest);

            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok(jwtService.generateToken(authRequest.getUsername()));
            } else {
                throw new BadCredentialsException("Invalid credentials");
            }
        } catch (BadCredentialsException e) {
            // To handle user enumeration safely, provide a generic error message
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    private Authentication authenticateUser(AuthRequest authRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
    }
}
