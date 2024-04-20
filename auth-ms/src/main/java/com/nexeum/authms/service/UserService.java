package com.nexeum.authms.service;

import com.nexeum.authms.dto.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> signUp(SignUpRequest request);
}
