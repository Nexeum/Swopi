package com.nexeum.userms.service;

import com.nexeum.userms.dto.request.CreateUpdateAddressRequest;
import com.nexeum.userms.dto.request.CreateUserRequest;
import com.nexeum.userms.dto.request.UserInfoRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> createUser(CreateUserRequest request);

    ResponseEntity<?> getUserInfo(UserInfoRequest request);

    ResponseEntity<?> createUpdateAddress(CreateUpdateAddressRequest request);
}
