package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.requests.BuyerSignupDto;
import com.waa.marketplace.dtos.requests.SellerSignupDto;
import com.waa.marketplace.dtos.responses.BuyerResponseDto;
import com.waa.marketplace.dtos.responses.SellerResponseDto;
import com.waa.marketplace.entites.Buyer;
import com.waa.marketplace.entites.Seller;
import com.waa.marketplace.entites.User;
import com.waa.marketplace.enums.Role;
import com.waa.marketplace.repositories.BuyerRepository;
import com.waa.marketplace.repositories.SellerRepository;
import com.waa.marketplace.repositories.UserRepository;
import com.waa.marketplace.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupServiceImpl implements SignupService {
    private final SellerRepository sellerRepository;
    private final BuyerRepository buyerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignupServiceImpl(SellerRepository sellerRepository, BuyerRepository buyerRepository,
                             UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.sellerRepository = sellerRepository;
        this.buyerRepository = buyerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SellerResponseDto sellerSignup(SellerSignupDto sellerSignupDto) {
        User user = generateUser(sellerSignupDto.getEmail(), sellerSignupDto.getName(), sellerSignupDto.getPassword()
                , Role.SELLER.name());
        Seller seller = Seller.builder().user(user).approved(false).build();

        Seller savedSeller = sellerRepository.save(seller);
        return SellerResponseDto.builder()
                .id(savedSeller.getId())
                .name(savedSeller.getUser().getName())
                .email(savedSeller.getUser().getEmail())
                .build();
    }

    @Override
    public BuyerResponseDto buyerSignup(BuyerSignupDto buyerSignupDto) {
        User user = generateUser(buyerSignupDto.getEmail(), buyerSignupDto.getName(), buyerSignupDto.getPassword(),
                Role.BUYER.name());
        Buyer buyer = Buyer.builder().user(user).shippingAddress(buyerSignupDto.getShippingAddress())
                .billingAddress(buyerSignupDto.getBillingAddress()).build();

        Buyer savedBuyer = buyerRepository.save(buyer);
        return BuyerResponseDto.builder().id(savedBuyer.getId()).email(savedBuyer.getUser().getEmail())
                .name(savedBuyer.getUser().getName()).billingAddress(savedBuyer.getBillingAddress())
                .shippingAddress(savedBuyer.getShippingAddress()).build();
    }

    private User generateUser(String email, String name, String password, String role) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }
        return User.builder().email(email).name(name).password(passwordEncoder.encode(password)).role(role).build();
    }
}
