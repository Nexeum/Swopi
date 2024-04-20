package com.nexeum.authms.controller;

import com.nexeum.authms.dto.request.AddRoleRequest;
import com.nexeum.authms.service.AdminService;
import com.nexeum.authms.service.RequestValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    private RequestValidationService requestValidationService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/add_role")
    public ResponseEntity<?> addRole(@RequestBody AddRoleRequest request, BindingResult result){
        ResponseEntity<?> requestErrors = requestValidationService.requestValidator(result);

        if(requestErrors != null){
            return requestErrors;
        }else{
            ResponseEntity<?> response = adminService.addRole(request);
            return response;
        }
    }
}
