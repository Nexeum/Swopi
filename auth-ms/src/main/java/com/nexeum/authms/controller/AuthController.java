package com.nexeum.authms.controller;

import com.nexeum.authms.dto.request.SignInRequest;
import com.nexeum.authms.dto.request.SignUpRequest;
import com.nexeum.authms.dto.response.JWTTokenResponse;
import com.nexeum.authms.security.JWTTokenProvider;
import com.nexeum.authms.service.RequestValidationService;
import com.nexeum.authms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestValidationService requestValidationService;

    @Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${app.jwt.token.prefix}")
    private String tokenPrefix;

    @PostMapping("/signup")
    public ResponseEntity<?> singup(@RequestBody SignUpRequest request, BindingResult result) {

        ResponseEntity<?> requestErrors = requestValidationService.requestValidator(result);

        if (requestErrors != null) {
            return requestErrors;
        } else {
            ResponseEntity<?> response = userService.signUp(request);
            return response;
        }

    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest request, BindingResult result) throws UnsupportedEncodingException {

        ResponseEntity<?> requestErrors = requestValidationService.requestValidator(result);
        JWTTokenResponse response = new JWTTokenResponse();

        if (requestErrors != null) {
            return requestErrors;
        } else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenPrefix + " " + tokenProvider.generateToken(authentication);

            response.setToken(jwt);
            response.setSuccess(true);

            return new ResponseEntity<JWTTokenResponse>(response, HttpStatus.OK);
        }


    }

}