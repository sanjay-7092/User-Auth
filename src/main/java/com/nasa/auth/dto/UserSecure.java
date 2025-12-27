package com.nasa.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSecure {

    private String currentPassword;
    private String newPassword;
}
