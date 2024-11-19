package com.waa.marketplace.services;

import com.waa.marketplace.dtos.requests.LoginRequestDto;
import com.waa.marketplace.dtos.responses.LoginResponseDto;
import com.waa.marketplace.dtos.requests.RefreshTokenRequestDto;

public interface AuthenticationService {
    LoginResponseDto login(LoginRequestDto loginRequest);

    LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest);
}
