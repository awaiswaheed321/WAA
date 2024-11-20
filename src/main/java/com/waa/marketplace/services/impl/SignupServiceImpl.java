package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.requests.SignupRequestDto;
import com.waa.marketplace.dtos.responses.SignupResponseDto;
import com.waa.marketplace.entites.Buyer;
import com.waa.marketplace.entites.Cart;
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
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        User user = generateUser(signupRequestDto.getEmail(), signupRequestDto.getName(), signupRequestDto.getPassword(),
                signupRequestDto.getRole());

        if(signupRequestDto.getRole().equals(Role.SELLER.name())) {
            Seller seller = Seller.builder().user(user).approved(false).build();
            sellerRepository.save(seller);
            return SignupResponseDto.builder().id(seller.getId()).email(seller.getUser().getEmail())
                    .name(seller.getUser().getName()).build();
        } else {
            Buyer buyer = Buyer.builder().user(user).build();
            Cart cart = Cart.builder().buyer(buyer).build();
            buyer.setCart(cart);
            buyerRepository.save(buyer);
            return SignupResponseDto.builder().id(buyer.getId()).email(buyer.getUser().getEmail())
                    .name(buyer.getUser().getName()).build();
        }
    }

    private User generateUser(String email, String name, String password, String role) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }
        return User.builder().email(email).name(name).password(passwordEncoder.encode(password)).role(role).build();
    }
}
