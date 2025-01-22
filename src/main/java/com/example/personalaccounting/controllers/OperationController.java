package com.example.personalaccounting.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.personalaccounting.repositories.OperationRepository;

@Controller
@RequestMapping("/operation")
public class OperationController {
    private OperationRepository operationRepository;

    @GetMapping
    public String getOperations(Model Model) {
        Model.addAttribute("operations", operationRepository.findAll());
        return "operation-listing";
    }
}
