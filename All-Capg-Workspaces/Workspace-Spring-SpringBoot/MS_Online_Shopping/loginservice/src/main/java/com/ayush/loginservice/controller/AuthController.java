package com.ayush.loginservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ayush.loginservice.dto.LoginRequest;
import com.ayush.loginservice.dto.RegisterRequest;
import com.ayush.loginservice.entity.User;
import com.ayush.loginservice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return service.login(request.getEmail(), request.getPassword());
    }
}