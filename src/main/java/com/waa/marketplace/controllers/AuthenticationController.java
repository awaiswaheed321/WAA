package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.LoginRequestDto;
import com.waa.marketplace.dtos.LoginResponseDto;
import com.waa.marketplace.dtos.RefreshTokenRequestDto;
import com.waa.marketplace.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
@Tag(name = "Authentication APIs", description = "APIs for user authentication and token management")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * User Login API
     * This endpoint authenticates a user using their email and password.
     * Upon successful authentication, it returns a JWT token and a refresh token.
     *
     * @param loginRequest DTO containing the email and password of the user.
     * @return LoginResponseDto with JWT token and refresh token.
     */
    @Operation(
            summary = "User Login",
            description = "Authenticates a user and returns a JWT token and refresh token upon successful login."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "Login credentials (email and password)", required = true)
            @RequestBody LoginRequestDto loginRequest
    ) {
        LoginResponseDto loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }

    /**
     * Refresh Token API
     * This endpoint generates a new JWT token using a valid refresh token.
     * The refresh token is validated, and a new token is issued if the refresh token is still valid.
     *
     * @param refreshTokenRequest DTO containing the refresh token.
     * @return LoginResponseDto with a new JWT token and refresh token.
     */
    @Operation(
            summary = "Refresh Token",
            description = "Generates a new JWT token using a valid refresh token."
    )
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(
            @Parameter(description = "Request body containing the refresh token", required = true)
            @RequestBody RefreshTokenRequestDto refreshTokenRequest
    ) {
        LoginResponseDto refreshTokenRes = authenticationService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok().body(refreshTokenRes);
    }
}
