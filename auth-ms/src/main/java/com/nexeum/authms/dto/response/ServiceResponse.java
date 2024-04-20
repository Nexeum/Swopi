package com.nexeum.authms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceResponse {
    private String code;
    private String message;
}
