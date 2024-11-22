package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.requests.LoginRequestDto;
import com.waa.marketplace.dtos.requests.RefreshTokenRequestDto;
import com.waa.marketplace.dtos.requests.SignupRequestDto;
import com.waa.marketplace.dtos.responses.LoginResponseDto;
import com.waa.marketplace.dtos.responses.SignupResponseDto;
import com.waa.marketplace.entites.Seller;
import com.waa.marketplace.entites.User;
import com.waa.marketplace.enums.Role;
import com.waa.marketplace.repositories.SellerRepository;
import com.waa.marketplace.repositories.UserRepository;
import com.waa.marketplace.security.jwt.JwtHelper;
import com.waa.marketplace.services.AuthenticationService;
import com.waa.marketplace.services.SignupService;
import com.waa.marketplace.utils.DtoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final SignupService signupService;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtHelper jwtHelper,
                                     SignupService signupService, UserRepository userRepository, SellerRepository sellerRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.signupService = signupService;
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
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
        LoginResponseDto res = new LoginResponseDto(accessToken, refreshToken, DtoMapper.mapToUserResponseDto(user));
        if(user.getRole().equals(Role.SELLER.name())) {
            Seller seller = sellerRepository.findByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("No related Seller Object found."));
            res.getUser().setApproved(seller.getApproved());
        }
        return res;
    }

    @Override
    public LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest) {
        boolean isRefreshTokenValid = jwtHelper.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        if (isRefreshTokenValid) {
            String email = jwtHelper.getSubject(refreshTokenRequest.getRefreshToken());
            final String accessToken = jwtHelper.generateToken(email);
            User user = userRepository.findByEmail(email);
            return new LoginResponseDto(accessToken, refreshTokenRequest.getRefreshToken(),
                    DtoMapper.mapToUserResponseDto(user));
        }
        throw new IllegalArgumentException("Refresh Token invalid");
    }

    @Override
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        return signupService.signup(signupRequestDto);
    }
}
