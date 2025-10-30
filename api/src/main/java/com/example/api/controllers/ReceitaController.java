package com.example.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.ReceitaDTO;
import com.example.api.entity.Receita;
import com.example.api.repository.UserRepository;
import com.example.api.services.ReceitaService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService service;

    

   
    @GetMapping("/me")
    public ResponseEntity minhasReceitas(JwtAuthenticationToken token){
        return ResponseEntity.ok(service.receitasUsuario(token));

    }
    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity create(@RequestBody ReceitaDTO receita,JwtAuthenticationToken jwt) {

        return ResponseEntity.ok(service.create(receita,jwt));
    }

    @GetMapping("/all")
    public ResponseEntity list() {
        return ResponseEntity.ok(service.list());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity update(@RequestParam Long id, @RequestBody ReceitaDTO receita){

        return ResponseEntity.ok(service.update(receita,id)) ;

    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity delete(@PathVariable Long id,JwtAuthenticationToken jwt) {
        service.delete(id,jwt);
        return ResponseEntity.noContent().build();
    }
    
   

}
