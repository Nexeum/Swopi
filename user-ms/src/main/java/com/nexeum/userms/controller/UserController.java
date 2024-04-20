package com.nexeum.userms.controller;

import com.nexeum.userms.dto.request.CreateUpdateAddressRequest;
import com.nexeum.userms.dto.request.CreateUserRequest;
import com.nexeum.userms.dto.request.UserInfoRequest;
import com.nexeum.userms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        ResponseEntity<?> response = userService.createUser(request);
        return response;
    }

    @PostMapping("/get-user-info")
    public ResponseEntity<?> getUserInfo(@RequestBody UserInfoRequest request) {
        ResponseEntity<?> response = userService.getUserInfo(request);
        return response;
    }

    @PostMapping("/create-update-address")
    public ResponseEntity<?> createUpdateAddress(@RequestBody CreateUpdateAddressRequest request){
        ResponseEntity<?> response = userService.createUpdateAddress(request);
        return response;
    }

}