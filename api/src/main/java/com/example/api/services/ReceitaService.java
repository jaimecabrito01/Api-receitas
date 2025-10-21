package com.example.api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.dto.ReceitaDTO;
import com.example.api.entity.Receita;
import com.example.api.entity.User;
import com.example.api.repository.ReceitaRepository;
import com.example.api.repository.UserRepository;

@Service
public class ReceitaService {
    @Autowired
    private ReceitaRepository receitaRepository;
    private UserRepository userRepository;

    public Receita create(ReceitaDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Receita receita = new Receita();
        receita.setTitulo(dto.getTitulo());
        receita.setDescricao(dto.getDescricao());
        receita.setUser(user);

        return receitaRepository.save(receita);
    }

    public List<Receita> list() {
        return receitaRepository.findAll();
    }

    public void delete(Long id) {
        receitaRepository.deleteById(id);
    }

    public Receita update(ReceitaDTO receita, Long id) {
        Receita novaReceita = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
        novaReceita.setCreatedAt(LocalDateTime.now());
        novaReceita.setDescricao(receita.getDescricao());
        novaReceita.setIngredientes(receita.getIngredientes());
        novaReceita.setPassos(receita.getPassos());
        novaReceita.setTitulo(receita.getTitulo());

        return receitaRepository.save(novaReceita);
    }

}
