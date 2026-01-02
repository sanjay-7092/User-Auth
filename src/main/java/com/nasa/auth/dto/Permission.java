package com.nasa.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Permission implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Boolean isAvail;
}
