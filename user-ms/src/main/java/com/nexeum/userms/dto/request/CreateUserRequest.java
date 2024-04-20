package com.nexeum.userms.dto.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private Long userId;
    private String username;
    private String email;
    private Address address;
    private Long cartId;
}
