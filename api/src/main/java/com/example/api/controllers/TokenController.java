package com.example.api.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.LoginRequest;
import com.example.api.dto.LoginResponse;
import com.example.api.repository.UserRepository;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    @Autowired
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenController(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> auth(@RequestBody LoginRequest loginRequest) {

        var user = repository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("usuario ou senha incorretos");

        }
        var expiresIn =  300l;
        var now = Instant.now();

        var claims = JwtClaimsSet.builder()
        .issuer("api-receitas")
        .subject(user.get().getId().toString(expiresIn))
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiresIn))
        .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.ok(new LoginResponse(jwtValue,expiresIn));

    }

}
