package com.example.personal_accounting.user_specific;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.personal_accounting.settings.UserSettingsService;

@Service
public class UserNumberService {
    @Autowired
    private UserNumberRepository userNumberRepository;

    @Autowired
    private UserSettingsService userSettingsService;
    
    public void createUserNumberFor(String username) {
        UserNumber userNumber = new UserNumber();
        userNumber.setUsername(username);

        userNumberRepository.save(userNumber);
    }

    public Optional<UserNumber> getUserNumber(String username) {
        return userNumberRepository.findByUsername(username);
    }

    public UserNumber getCurrentUserNumber() {
        return getUserNumber(userSettingsService.getCurrentUsername()).orElseThrow();
    }
}
