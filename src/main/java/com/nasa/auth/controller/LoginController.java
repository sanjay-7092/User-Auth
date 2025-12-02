package com.nasa.auth.controller;

import com.nasa.auth.DTO.UserLogin;
import com.nasa.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {


    private final AuthService authService;

    protected LoginController(AuthService authService){
        this.authService=authService;
    }
    @PostMapping
    public ResponseEntity<String> login(@RequestBody UserLogin userLogin){
        String response = authService.login(userLogin);
        return ResponseEntity.ok(response);
    }
}
