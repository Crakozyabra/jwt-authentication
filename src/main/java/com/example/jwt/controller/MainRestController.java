package com.example.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/")
public class MainRestController {

    @GetMapping("/unsecured")
    public String unsecured(Principal principal) {
        return String.format("unsecured data. principal: %s", Objects.isNull(principal) ? null : principal.getName());
    }

    @GetMapping("/secured")
    public String secured(Principal principal) {
        return String.format("secured data. principal: %s", principal.getName());
    }

    @GetMapping("/admin")
    public String admin(Principal principal) {
        return String.format("admin data. principal: %s", principal.getName());
    }

    @GetMapping("/user")
    public String user(Principal principal) {
        return String.format("user data. principal: %s", principal.getName());
    }
}