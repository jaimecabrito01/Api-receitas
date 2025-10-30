package com.example.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.api.dto.ReceitaDTO;
import com.example.api.dto.ReceitaResponseDTO;
import com.example.api.entity.Receita;
import com.example.api.entity.User;
import com.example.api.repository.ReceitaRepository;
import com.example.api.repository.UserRepository;

@Service
public class ReceitaService {
    @Autowired
    private ReceitaRepository receitaRepository;
    @Autowired
    private UserRepository userRepository;

    public Receita create(ReceitaDTO dto,JwtAuthenticationToken token) {
        User user = userRepository.findById(UUID.fromString(token.getName()))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Receita receita = new Receita();
        receita.setTitulo(dto.getTitulo());
        receita.setDescricao(dto.getDescricao());
        receita.setCreatedAt(LocalDateTime.now());
        receita.setIngredientes(dto.getIngredientes());
        receita.setPassos(dto.getPassos());
        receita.setUser(user);

        return receitaRepository.save(receita);
    }

    public List<ReceitaResponseDTO> list() {
        return receitaRepository.findAllReceitas();
    }

    public void delete(Long id,JwtAuthenticationToken jwtAuthenticationToken) {
        receitaRepository.deleteById(id);
    }
    public List<Receita> receitasUsuario(JwtAuthenticationToken jwt){
        return receitaRepository.findAllByUserId(UUID.fromString(jwt.getName()));
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
    public ReceitaDTO receitaEspec(Long id){
 Receita receita = receitaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

    // Transforma em DTO
    ReceitaDTO dto = new ReceitaDTO(
        receita.getTitulo(),
        receita.getDescricao(),
        receita.getIngredientes(),
        receita.getPassos(),
        receita.getCreatedAt()
    );
        return dto;
    }

}
