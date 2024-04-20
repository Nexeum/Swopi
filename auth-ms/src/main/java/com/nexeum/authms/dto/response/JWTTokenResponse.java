package com.nexeum.authms.dto.response;

import lombok.Data;

@Data
public class JWTTokenResponse {
    private boolean success;
    private String token;
}
