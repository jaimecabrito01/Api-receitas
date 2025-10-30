package com.example.api.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.dto.UserCreateDTO;
import com.example.api.entity.Role;
import com.example.api.repository.RoleRepository;
import com.example.api.repository.UserRepository;
import com.example.api.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;
   
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserCreateDTO userCreateDTO) {

      
        var user = userRepository.findByEmail(userCreateDTO.getEmail());
        if(user.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok(service.create(userCreateDTO));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity me(@RequestBody JwtAuthenticationToken jwt){
        var user = userRepository.findById(UUID.fromString(jwt.getName())).orElseThrow(()-> new RuntimeException("user not found"));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity list() {
        return ResponseEntity.ok(service.list());
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody UserCreateDTO dto,JwtAuthenticationToken jwyAuthenticationToken) {
        service.update(dto,jwyAuthenticationToken);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@RequestBody JwtAuthenticationToken jwt) {
        service.delete(jwt);
        return ResponseEntity.noContent().build();
    }

}
