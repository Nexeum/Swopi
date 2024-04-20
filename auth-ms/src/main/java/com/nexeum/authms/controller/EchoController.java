package com.nexeum.authms.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

public class EchoController {
    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public String echo(Principal principal){
        return principal.getName();
    }
}
