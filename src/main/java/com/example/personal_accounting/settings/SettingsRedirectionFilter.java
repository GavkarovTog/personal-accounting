package com.example.personal_accounting.settings;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SettingsRedirectionFilter extends OncePerRequestFilter {
    @Autowired
    private UserSettingsHolder userSettingsHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (userSettingsHolder.hasSetup() || notRequireSetup(request)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect("/settings");
        }
    }

    private boolean notRequireSetup(HttpServletRequest request) {
        String uri = request.getRequestURI();

        if (uri.startsWith("/css/")) {
            return true;
        }

        return List.of("/register", "/login", "/settings")
            .stream()
            .anyMatch(prefix -> uri.equals(prefix));
    }
}