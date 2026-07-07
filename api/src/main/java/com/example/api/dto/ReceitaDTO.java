package com.example.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReceitaDTO {
    private String titulo;
    private String descricao;
    private String imagem;
    private String categoria;
    private List<String> ingredientes;
    private List<String> passos;
    private LocalDateTime createdAt = LocalDateTime.now();

    public ReceitaDTO() {
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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<String> getPassos() {
        return passos;
    }

    public void setPassos(List<String> passos) {
        this.passos = passos;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ReceitaDTO(String titulo, String descricao, String imagem, String categoria, List<String> ingredientes, List<String> passos,
            LocalDateTime createdAt) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagem = imagem;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.passos = passos;
        this.createdAt = createdAt;
    }
}
