package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.requests.LoginRequestDto;
import com.waa.marketplace.dtos.requests.RefreshTokenRequestDto;
import com.waa.marketplace.dtos.requests.SignupRequestDto;
import com.waa.marketplace.dtos.responses.LoginResponseDto;
import com.waa.marketplace.dtos.responses.SignupResponseDto;
import com.waa.marketplace.entites.User;
import com.waa.marketplace.repositories.UserRepository;
import com.waa.marketplace.security.jwt.JwtHelper;
import com.waa.marketplace.services.AuthenticationService;
import com.waa.marketplace.services.SignupService;
import com.waa.marketplace.utils.DtoMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final SignupService signupService;
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtHelper jwtHelper,
                                     SignupService signupService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.signupService = signupService;
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        final String accessToken = jwtHelper.generateToken(loginRequest.getEmail());
        final String refreshToken = jwtHelper.generateRefreshToken(loginRequest.getEmail());
        User user = userRepository.findByEmail(loginRequest.getEmail());
        return new LoginResponseDto(accessToken, refreshToken, DtoMapper.mapToUserResponseDto(user));
    }

    @Override
    public LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest) {
        boolean isRefreshTokenValid = jwtHelper.validateToken(refreshTokenRequest.getRefreshToken());
        if (isRefreshTokenValid) {
            String email = jwtHelper.getSubject(refreshTokenRequest.getRefreshToken());
            final String accessToken = jwtHelper.generateToken(email);
            User user = userRepository.findByEmail(email);
            return new LoginResponseDto(accessToken, refreshTokenRequest.getRefreshToken(),
                    DtoMapper.mapToUserResponseDto(user));
        }
        return new LoginResponseDto();
    }

    @Override
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        return signupService.signup(signupRequestDto);
    }
}
