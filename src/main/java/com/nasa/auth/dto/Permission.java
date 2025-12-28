package com.nasa.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permission {
    private Long id;
    private String name;
    private String description;
    private Boolean isAvail;
}
