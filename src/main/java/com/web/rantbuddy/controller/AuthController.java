package com.web.rantbuddy.controller;


import com.web.rantbuddy.model.LoginResponse;
import com.web.rantbuddy.model.User;
import com.web.rantbuddy.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newuser) {
        boolean user = authService.register(newuser);
        return ResponseEntity.ok(200);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody User loginuser) {
        return authService.login(loginuser.getUsername(), loginuser.getPassword());
    }

}