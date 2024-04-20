package com.nexeum.authms.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Set<User> users;
}
