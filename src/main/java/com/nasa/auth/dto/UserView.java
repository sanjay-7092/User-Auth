package com.nasa.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserView {
    private Long id;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private Set<Role> roles;
    private String email;
    private Boolean isActive;
}
