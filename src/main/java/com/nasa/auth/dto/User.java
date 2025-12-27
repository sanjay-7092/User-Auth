package com.nasa.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class User {

    private Long id;
    @Size(max=20, message="First name is too large")
    private String firstName;
    @Size(max=20, message="Last name is too large")
    private String lastName;
    @Email(message="Email is invalid format")
    private String email;
    @Size(min=10, max=10, message="Invalid contact Number")
    private String contactNumber;
    private String role;
    private LocalDate dob;
    @Size(min=8, max=15, message="Password must be 8–15 chars")
    private String password;
    private String confirmPassword;
    private Boolean isActive;

}
