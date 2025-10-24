package com.example.api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {
    private final JwtService service;
    
    public AuthenticationService(JwtService service) {
        this.service = service;
    }
    
    public String authenticate (UserDetails authentication){
        return service.getGeneratedToken(authentication);
    }
}
