package edu.miu.labs.service;

import edu.miu.labs.entities.dtos.LoginRequestDto;
import edu.miu.labs.entities.dtos.LoginResponseDto;
import edu.miu.labs.entities.dtos.RefreshTokenRequestDto;

public interface AuthenticationService {
    LoginResponseDto login(LoginRequestDto loginRequest);
    LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest);
}
