package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.requests.LoginRequestDto;
import com.waa.marketplace.dtos.requests.RefreshTokenRequestDto;
import com.waa.marketplace.dtos.requests.SignupRequestDto;
import com.waa.marketplace.dtos.responses.LoginResponseDto;
import com.waa.marketplace.dtos.responses.SignupResponseDto;
import com.waa.marketplace.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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
    public ResponseEntity<LoginResponseDto> login(
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
    public ResponseEntity<LoginResponseDto> refreshToken(
            @Parameter(description = "Request body containing the refresh token", required = true)
            @RequestBody RefreshTokenRequestDto refreshTokenRequest
    ) {
        LoginResponseDto refreshTokenRes = authenticationService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok().body(refreshTokenRes);
    }

    /**
     * Signup API
     * <p>
     * This endpoint allows users to register as a Buyer or Seller in the system.
     * The role is determined based on the provided data in the signup request.
     *
     * @param signupRequestDto Contains the details required to create a new user account,
     *                         such as email, name, password, and role (BUYER or SELLER).
     * @return A ResponseEntity containing the details of the created user, including their
     * ID, name, and email.
     */
    @Operation(
            summary = "User Signup",
            description = "Registers a new user in the system as either a Buyer or Seller. The role is defined in the" +
                    " request."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SignupResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data or role",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(
            @RequestBody @Valid @Schema(description = "Signup details including email, name, password, and role")
            SignupRequestDto signupRequestDto
    ) {
        SignupResponseDto signupResponseDto = authenticationService.signup(signupRequestDto);
        return ResponseEntity.ok(signupResponseDto);
    }
}
