package com.example.api.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.api.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    @Autowired
      private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
         if (!roleRepository.existsByNome("admin")) {
            roleRepository.save(new Role("admin"));
        }
        if (!roleRepository.existsByNome("basic")) {
            roleRepository.save(new Role("basic"));
        }
    }
    
}
