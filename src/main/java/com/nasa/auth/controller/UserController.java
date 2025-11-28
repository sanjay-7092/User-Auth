package com.nasa.auth.controller;

import com.nasa.auth.DTO.User;
import com.nasa.auth.DTO.UserView;
import com.nasa.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserView> signUp(@RequestBody User user){
        return ResponseEntity.ok(userService.signUp(user));

    }
}
