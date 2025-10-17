package com.example.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.UserCreateDTO;
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

    @PostMapping("/create")
    public ResponseEntity postMethodName(@RequestBody UserCreateDTO userCreateDTO) {
   
        return ResponseEntity.ok(service.create(userCreateDTO));
    }
    @GetMapping("/all")
    public ResponseEntity list() {
        return ResponseEntity.ok(service.list());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UserCreateDTO dto) {
        service.update(dto, id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
}
