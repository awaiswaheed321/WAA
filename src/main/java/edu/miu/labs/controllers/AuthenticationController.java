package edu.miu.labs.controllers;

import edu.miu.labs.entities.dtos.LoginRequestDto;
import edu.miu.labs.entities.dtos.LoginResponseDto;
import edu.miu.labs.entities.dtos.RefreshTokenRequestDto;
import edu.miu.labs.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        LoginResponseDto refreshTokenRes = authenticationService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok().body(refreshTokenRes);
    }
}
