package com.nasa.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class UserView implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String userId;
    private Set<Role> roles;
    private String email;
    private Boolean isActive;
}
