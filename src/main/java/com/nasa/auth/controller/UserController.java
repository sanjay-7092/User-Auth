package com.nasa.auth.controller;

import com.nasa.auth.dto.User;
import com.nasa.auth.dto.UserSecure;
import com.nasa.auth.dto.UserView;
import com.nasa.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/sign-up")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserView> signUp(@Valid @RequestBody User user){
        return ResponseEntity.ok(userService.signUp(user));
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_VISOR','ROLE_OPERATOR')")
    public ResponseEntity<List<UserView>> getAllUsers(){
        List<UserView> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_VISOR','ROLE_OPERATOR')")
    public ResponseEntity<UserView> getByUserId(@PathVariable("userId") Long userId){
        UserView userView = userService.getByUserId(userId);
        return ResponseEntity.ok(userView);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_VISOR')")
    public ResponseEntity<UserView> updateUser(@RequestBody User user,@PathVariable("userId") Long userId){
        UserView userView =userService.updateByID(userId,user);
        return ResponseEntity.ok(userView);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("User Deleted Successfully");
    }
    @PostMapping("/change-password/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
    public ResponseEntity<String> changePassword(@RequestBody UserSecure userSecure,@PathVariable(name="id") Long id){
        userService.changePassword(userSecure,id);
        return ResponseEntity.ok("Password Changed Successfully");
    }
}
