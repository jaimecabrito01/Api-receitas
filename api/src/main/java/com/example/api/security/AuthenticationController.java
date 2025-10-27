package com.example.api.security;

import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.AuthDTO;
import com.example.api.repository.UserRepository;

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
  @Autowired
  private UserRepository repository;

    @PostMapping("/authenticate")
     public ResponseEntity login(@RequestBody  AuthDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

  }
    
    

