package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.requests.LoginRequestDto;
import com.waa.marketplace.dtos.requests.SignupRequestDto;
import com.waa.marketplace.dtos.responses.LoginResponseDto;
import com.waa.marketplace.dtos.requests.RefreshTokenRequestDto;
import com.waa.marketplace.dtos.responses.SignupResponseDto;
import com.waa.marketplace.entites.User;
import com.waa.marketplace.security.jwt.JwtHelper;
import com.waa.marketplace.services.AuthenticationService;
import com.waa.marketplace.services.SignupService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final SignupService signupService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtHelper jwtHelper, SignupService signupService) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.signupService = signupService;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        final String accessToken = jwtHelper.generateToken(loginRequest.getEmail());
        final String refreshToken = jwtHelper.generateRefreshToken(loginRequest.getEmail());
        return new LoginResponseDto(accessToken, refreshToken);
    }

    @Override
    public LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest) {
        boolean isRefreshTokenValid = jwtHelper.validateToken(refreshTokenRequest.getRefreshToken());
        if (isRefreshTokenValid) {
            final String accessToken = jwtHelper.generateToken(jwtHelper.getSubject(refreshTokenRequest.getRefreshToken()));
            return new LoginResponseDto(accessToken, refreshTokenRequest.getRefreshToken());
        }
        return new LoginResponseDto();
    }

    @Override
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        return signupService.signup(signupRequestDto);
    }
}
