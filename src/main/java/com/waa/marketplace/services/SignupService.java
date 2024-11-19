package com.waa.marketplace.services;

import com.waa.marketplace.dtos.requests.SignupRequestDto;
import com.waa.marketplace.dtos.responses.SignupResponseDto;

public interface SignupService {
    SignupResponseDto signup(SignupRequestDto signupRequestDto);
}
