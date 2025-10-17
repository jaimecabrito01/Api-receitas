package com.example.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.ReceitaDTO;
import com.example.api.entity.Receita;
import com.example.api.services.ReceitaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody ReceitaDTO receita) {

        return ResponseEntity.ok(service.create(receita));
    }

    @GetMapping("/all")
    public ResponseEntity list() {
        return ResponseEntity.ok(service.list());
    }

    @PutMapping("/update/{id}")
    public Receita update(@PathVariable Long id, @RequestBody ReceitaDTO receita){

        return service.update(receita,id);

    }
    
   

}
