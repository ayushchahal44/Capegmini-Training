package com.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/user/home")
    public String userHome() {
        return "Welcome Spirng Security  User!";
    }
    @GetMapping("/admin/home")
    public String adminHome() {
        return "Welcome Spirng Security Admin!";
    }
}

