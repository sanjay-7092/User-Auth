package com.nasa.auth.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String role;
    private LocalDate dob;
    private String password;
    private Boolean isActive;

}
