package com.nexeum.authms.service;

import com.nexeum.authms.dto.request.AddRoleRequest;
import com.nexeum.authms.dto.response.ServiceResponse;
import com.nexeum.authms.entity.Role;
import com.nexeum.authms.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService{
    private RoleRepository roleRepository;

    public AdminServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<?> addRole(AddRoleRequest request) {
        ResponseEntity<?> response;
        ServiceResponse serviceResponse;

        try{
            Role savedRole = roleRepository.findRoleByName(request.getRoleName());
            if(savedRole != null){
                serviceResponse = new ServiceResponse("400", "Role already exists");
                response = ResponseEntity.badRequest().body(serviceResponse);
            } else {
                Role role = new Role();
                role.setName(request.getRoleName());
                roleRepository.save(role);
                serviceResponse = new ServiceResponse("200", "Role added successfully");
                response = ResponseEntity.ok().body(serviceResponse);
            }
            return response;
        }catch(Exception e){
            log.error("Error adding role: ", e);
            serviceResponse = new ServiceResponse("500", "Error adding role");
            response = ResponseEntity.badRequest().body(serviceResponse);
            return response;
        }
    }

}
