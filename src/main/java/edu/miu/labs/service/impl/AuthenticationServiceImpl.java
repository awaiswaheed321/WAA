package edu.miu.labs.service.impl;

import edu.miu.labs.entities.dtos.LoginRequestDto;
import edu.miu.labs.entities.dtos.LoginResponseDto;
import edu.miu.labs.entities.dtos.RefreshTokenRequestDto;
import edu.miu.labs.security.jwt.JwtHelper;
import edu.miu.labs.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
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
}
