package com.nexeum.authms.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class InvalidLoginResponse {
    private String username;
    private String password;
}
