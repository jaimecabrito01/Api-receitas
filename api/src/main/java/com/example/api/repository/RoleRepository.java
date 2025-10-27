package com.example.api.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
  Role findByNome(String nome);

    
} 
