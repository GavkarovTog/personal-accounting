package com.example.personal_accounting.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.personal_accounting.settings.UserSettingsService;

@Controller
public class MenuController {
    @Autowired
    private UserSettingsService userSettingsService;

    @GetMapping("/")
    public String menu(Model model) {
        model.addAttribute("username", userSettingsService.getCurrentUsername());
        return "index";
    }
}
