package com.nexeum.userms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nexeum.userms.entity.Address;
import com.nexeum.userms.entity.User;
import com.nexeum.userms.repository.UserRepository;
import com.nexeum.userms.dto.request.CreateUpdateAddressRequest;
import com.nexeum.userms.dto.request.CreateUserRequest;
import com.nexeum.userms.dto.request.UserInfoRequest;
import com.nexeum.userms.dto.response.ServiceResponse;
import com.nexeum.userms.dto.response.UserInfoResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> createUser(CreateUserRequest request) {

        User user = new User();
        Address address = new Address();

        ResponseEntity<?> responseEntity = null;
        ServiceResponse response = new ServiceResponse();

        try {
            user.setUserId(request.getUserId());
            user.setEmail(request.getEmail());
            user.setCartId(request.getCartId());

            if(request.getAddress() != null) {
                address.setAddressLine(request.getAddress().getAddressLine());
                address.setCity(request.getAddress().getCity());
                address.setPin(request.getAddress().getPin());
                address.setState(request.getAddress().getState());
                address.setCountry(request.getAddress().getCountry());
                user.setAddress(address);
            }

            userRepository.save(user);
            response.setCode("200");
            response.setMessage("User Data Persisted");
            responseEntity = new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            response.setCode("500");
            response.setMessage("Internal Server Error");
            responseEntity = new ResponseEntity<ServiceResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Override
    public ResponseEntity<?> getUserInfo(UserInfoRequest request) {

        ResponseEntity<?> responseEntity = null;
        ServiceResponse response = new ServiceResponse();
        try {
            User user = userRepository.findByUserId(request.getUserId()).orElse(null);
            if (user != null) {
                UserInfoResponse userInfo = new UserInfoResponse();
                userInfo.setUserId(user.getUserId());
                userInfo.setEmail(user.getEmail());

                if(!(userInfo.getAddress() == null)) {
                    com.nexeum.userms.dto.response.Address address = new com.nexeum.userms.dto.response.Address();
                    address.setAddressId(user.getAddress().getAddressId());
                    address.setAddressLine(user.getAddress().getAddressLine());
                    address.setCity(user.getAddress().getCity());
                    address.setPin(user.getAddress().getPin());
                    address.setState(user.getAddress().getState());
                    address.setCountry(user.getAddress().getCountry());
                    userInfo.setAddress(address);
                }

                userInfo.setCartId(user.getCartId());

                responseEntity = new ResponseEntity<UserInfoResponse>(userInfo, HttpStatus.OK);

            } else {
                response.setCode("404");
                response.setMessage("user information not found");
                responseEntity = new ResponseEntity<ServiceResponse>(response, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.error(e.toString());
            response.setCode("500");
            response.setMessage("Internal Server Error");
            responseEntity = new ResponseEntity<ServiceResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<?> createUpdateAddress(CreateUpdateAddressRequest request) {

        ResponseEntity<?> responseEntity = null;
        ServiceResponse response = new ServiceResponse();

        try {
            User user = userRepository.findByUserId(request.getUserId()).orElse(null);

            if (user != null) {
                Address address = new Address();
                address.setAddressLine(request.getAddressLine());
                address.setCity(request.getCity());
                address.setPin(request.getPin());
                address.setState(request.getPin());
                address.setCountry(request.getCountry());
                user.setAddress(address);
                userRepository.save(user);
                response.setCode("200");
                response.setMessage("Address information Synced");
                responseEntity = new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.toString());
            response.setCode("500");
            response.setMessage("Internal Server Error");
            responseEntity = new ResponseEntity<ServiceResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}