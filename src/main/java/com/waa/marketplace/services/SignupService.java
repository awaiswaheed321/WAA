package com.waa.marketplace.services;

import com.waa.marketplace.dtos.requests.BuyerSignupDto;
import com.waa.marketplace.dtos.requests.SellerSignupDto;
import com.waa.marketplace.dtos.responses.BuyerResponseDto;
import com.waa.marketplace.dtos.responses.SellerResponseDto;

public interface SignupService {
    SellerResponseDto sellerSignup(SellerSignupDto sellerSignupDto);

    BuyerResponseDto buyerSignup(BuyerSignupDto buyerSignupDto);
}
