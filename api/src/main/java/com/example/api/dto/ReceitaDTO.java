package com.example.api.dto;

import java.time.LocalDateTime;

public class ReceitaDTO {
    private String titulo;
    private String descricao;
    private Long userId;
    private String ingredientes;
    private String passos;
    private LocalDateTime createdAt = LocalDateTime.now();

    public ReceitaDTO() {
    }

public ReceitaDTO(String titulo, String descricao, Long userId, String ingredientes, String passos,
            LocalDateTime createdAt) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.userId = userId;
        this.ingredientes = ingredientes;
        this.passos = passos;
        this.createdAt = createdAt;
    }

    

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPassos() {
        return passos;
    }

    public void setPassos(String passos) {
        this.passos = passos;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
}
