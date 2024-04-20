package com.nexeum.userms.dto.response;

import lombok.Data;

@Data
public class UserInfoResponse {
    private Long userId;
    private String username;
    private String email;
    private Address address;
    private Long cartId;
}
