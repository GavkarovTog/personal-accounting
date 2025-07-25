package com.example.personal_accounting.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.example.personal_accounting.user_specific.UserNumberService;

@Service
public class SecurityService {
    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private UserNumberService userNumberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean suchUsernameExists(String username) {
        return userDetailsManager.userExists(username);
    }

    public void register(String username, String password) {
        UserDetails userDetails = User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .roles("USER")
            .build();

        userDetailsManager.createUser(userDetails);

        userNumberService.createUserNumberFor(username);
    }
}
