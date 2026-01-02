package com.nasa.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class Role implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Set<Permission> permissions;

}
