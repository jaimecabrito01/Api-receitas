package com.example.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.api.dto.ReceitaResponseDTO;
import com.example.api.entity.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long>{

  @Query("SELECT new com.example.api.dto.ReceitaResponseDTO(r.id,r.titulo, r.descricao, r.ingredientes, r.passos, r.createdAt, r.user.nome) " +
           "FROM Receita r")
    List<ReceitaResponseDTO> findAllReceitas();

    List<Receita> findAllByUserId(UUID id);
}
