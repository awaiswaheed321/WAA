package edu.miu.labs.security.service;

import edu.miu.labs.entities.User;
import edu.miu.labs.repositories.UserRepository;
import edu.miu.labs.security.dto.AccountDetails;
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
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new AccountDetails(user);
    }
}
