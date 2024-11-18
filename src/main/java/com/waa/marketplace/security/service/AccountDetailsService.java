package com.waa.marketplace.security.service;

import com.waa.marketplace.entites.User;
import com.waa.marketplace.repositories.UserRepository;
import com.waa.marketplace.security.dto.AccountDetails;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public AccountDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new AccountDetails(user);
    }
}
