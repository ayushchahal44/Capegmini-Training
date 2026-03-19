package com.ayush.loginservice.service;

import com.ayush.loginservice.entity.User;
import com.ayush.loginservice.repository.UserRepository;
import com.ayush.loginservice.config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword())); // 🔐 encrypt
        return repo.save(user);
    }

    public String login(String email, String password) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (encoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(email); // 🔑 TOKEN RETURN
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}