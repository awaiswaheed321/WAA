package com.waa.marketplace.services;

import com.waa.marketplace.dtos.LoginRequestDto;
import com.waa.marketplace.dtos.LoginResponseDto;
import com.waa.marketplace.dtos.RefreshTokenRequestDto;

public interface AuthenticationService {
    LoginResponseDto login(LoginRequestDto loginRequest);

    LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest);
}
