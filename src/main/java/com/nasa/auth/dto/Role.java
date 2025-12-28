package com.nasa.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Role {

    private Long id;
    private String name;
    private String description;
    private Set<Permission> permissions;

}
