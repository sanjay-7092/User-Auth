package com.nasa.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserView {
    private Long id;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String role;
    private String email;
    private Boolean isActive;
}
