package com.waa.marketplace.services;

import com.waa.marketplace.dtos.requests.LoginRequestDto;
import com.waa.marketplace.dtos.requests.SignupRequestDto;
import com.waa.marketplace.dtos.responses.LoginResponseDto;
import com.waa.marketplace.dtos.requests.RefreshTokenRequestDto;
import com.waa.marketplace.dtos.responses.SignupResponseDto;

public interface AuthenticationService {
    LoginResponseDto login(LoginRequestDto loginRequest);

    LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest);

    SignupResponseDto signup(SignupRequestDto signupRequestDto);
}
