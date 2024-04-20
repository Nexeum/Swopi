package com.nexeum.userms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    private Long id;
    private Long userId;
    private String username;
    private String email;
    @OneToOne
    private Address address;
    private Long cartId;
}
