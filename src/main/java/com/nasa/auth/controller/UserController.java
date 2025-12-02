package com.nasa.auth.controller;

import com.nasa.auth.DTO.User;
import com.nasa.auth.DTO.UserView;
import com.nasa.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<UserView>> getAllUsers(){
        List<UserView> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserView> getByUserId(@PathVariable("userId") Long userId){
        UserView userView = userService.getByUserId(userId);
        return ResponseEntity.ok(userView);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserView> updateUser(@RequestBody User user,@PathVariable("userId") Long userId){
        UserView userView =userService.updateByID(userId,user);
        return ResponseEntity.ok(userView);
    }
}
