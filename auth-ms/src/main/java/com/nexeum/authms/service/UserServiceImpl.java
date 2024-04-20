package com.nexeum.authms.service;

import com.nexeum.authms.dto.request.SignUpRequest;
import com.nexeum.authms.dto.response.ServiceResponse;
import com.nexeum.authms.dto.response.SignUpResponse;
import com.nexeum.authms.entity.Role;
import com.nexeum.authms.entity.User;
import com.nexeum.authms.repository.RoleRepository;
import com.nexeum.authms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.nexeum.authms.model.UserPrincipal;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findUserByUsername(username);

        if(user != null){
            return UserPrincipal.build(user);
        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Transactional
    public User loadUserById(long id){
        User user = userRepository.findById(id).orElse(null);

        if(user != null){
            return user;
        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> signUp(SignUpRequest request) {

        ResponseEntity<?> response;
        ServiceResponse serviceResponse;
        SignUpResponse signUpResponse = new SignUpResponse();

        try {
            if (userRepository.findUserByUsername(request.getUsername()) != null) {
                serviceResponse = new ServiceResponse("400","User already registered with " + request.getUsername()
                        + " please try with a different username");
                response = new ResponseEntity<ServiceResponse>(serviceResponse, HttpStatus.OK);
                return response;
            } else {
                User user = new User();
                user.setUsername(request.getUsername());
                user.setEmail(request.getEmail());
                user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

                Role userRole = roleRepository.findRoleByName("ROLE_USER");

                if (userRole != null) {
                    userRepository.save(user);
                    user.addRole(userRole);
                    User registeredUser = userRepository.save(user);

                    signUpResponse.setUserId(registeredUser.getId());
                    signUpResponse.setCode("200");
                    signUpResponse.setMessage(request.getUsername() + " you are successfully registered");
                    response = new ResponseEntity<SignUpResponse>(signUpResponse, HttpStatus.OK);

                } else {
                    serviceResponse = new ServiceResponse("401", "we are not accepting user registration at the moment. Please try again later");
                    response = new ResponseEntity<ServiceResponse>(serviceResponse, HttpStatus.OK);
                    return response;
                }
                return response;
            }

        } catch (Exception e) {
            log.error("{ There is an Error while SignUp }" + e.toString());
            serviceResponse = new ServiceResponse("500", "There is an error occured while signing you up. Please try after some time");
            response = new ResponseEntity<ServiceResponse>(serviceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }
}