package com.nasa.auth.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String lastName;
    private String firstName;
    private String email;
    private String contactNo;
    private String role;
    private LocalDate dob;
    private String password;
}
