package com.nasa.auth.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="lastname")
    private String lastName;
    @Column(name="firstname")
    private String firstName;
    @Column(name="email")
    private String email;
    @Column(name="contact_no")
    private String contactNumber;
    @Column(name="role")
    private String role;
    @Column(name="dob")
    private LocalDate dob;
    @Column(name="password")
    private String password;
    @Column(name="active")
    private Boolean isActive;
}
