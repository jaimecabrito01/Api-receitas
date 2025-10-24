package com.example.api.security;

import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.AuthDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController  
public class AuthenticationController {
    @Autowired
  private AuthenticationService authenticationService;
  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @PostMapping("/authenticate")
  public ResponseEntity authenticate(@RequestBody  AuthDTO authentication) {
 UserAuthenticated userDetails = (UserAuthenticated) userDetailsServiceImpl.loadUserByUsername(authentication.getEmail());

        // Verifica senha
        if (!passwordEncoder.matches(authentication.getSenha(), userDetails.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        // Gera JWT
        return ResponseEntity.ok(authenticationService.authenticate(userDetails));
    }
  }
    
    

