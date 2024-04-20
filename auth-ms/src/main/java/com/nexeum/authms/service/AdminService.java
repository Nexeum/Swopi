package com.nexeum.authms.service;

import com.nexeum.authms.dto.request.AddRoleRequest;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<?> addRole(AddRoleRequest addRoleRequest);
}
