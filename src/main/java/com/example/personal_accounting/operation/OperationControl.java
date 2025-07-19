package com.example.personal_accounting.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.personal_accounting.settings.UserSettingsService;

@RequestMapping(path = "/operation")
@Controller
public class OperationControl {
    @Autowired
    private UserSettingsService userSettingsService;

    @GetMapping
    public String getOperations(Model model) {
        model.addAttribute("username", userSettingsService.getCurrentUsername());
        return "operations";
    }

    
    @ModelAttribute("currency")
    public String currency() {
        return userSettingsService.getCurrentUserSetup().getCurrency();
    }
}
