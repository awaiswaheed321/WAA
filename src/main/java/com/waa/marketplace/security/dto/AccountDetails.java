package com.waa.marketplace.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.waa.marketplace.entites.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

public class AccountDetails implements UserDetails {
    @Getter
    private final Long id;
    private final String email;

    @JsonIgnore
    private final String password;
    private final String role;

    public AccountDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toUpperCase(Locale.ROOT)));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
