package com.example.api.security;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;


@RestController  
public class AuthenticationController {
    @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("authenticate")
  public String authenticate(Authentication authentication) {
    return authenticationService.authenticate(authentication);
  }
    
    
}
