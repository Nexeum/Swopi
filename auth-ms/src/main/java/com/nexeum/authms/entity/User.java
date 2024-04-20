package com.nexeum.authms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private Date createdDate;
    private Date updatedDate;

    public void addRole(Role role){
        roles.remove(role);
        role.getUsers().remove(this);
    }
}
