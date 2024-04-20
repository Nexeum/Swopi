package com.nexeum.authms.dto.response;

import lombok.Data;

@Data
public class SignUpResponse {
    private Long userId;
    private String code;
    private String message;
}
