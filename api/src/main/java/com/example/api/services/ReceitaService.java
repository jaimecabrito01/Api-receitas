package com.example.api.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${app.upload.dir}")
    private String uploadDir;

    public Receita create(ReceitaDTO dto,JwtAuthenticationToken token) {
        User user = userRepository.findById(UUID.fromString(token.getName()))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Receita receita = new Receita();
        receita.setTitulo(dto.getTitulo());
        receita.setDescricao(dto.getDescricao());
        receita.setCreatedAt(LocalDateTime.now());
        receita.setIngredientes(dto.getIngredientes());
        receita.setPassos(dto.getPassos());
        receita.setCategoria(dto.getCategoria());
        receita.setUser(user);

        return receitaRepository.save(receita);
    }

    public List<ReceitaResponseDTO> list() {
        return receitaRepository.findAllReceitas();
    }

    public List<ReceitaResponseDTO> listByCategoria(String categoria) {
        return receitaRepository.findAllReceitas().stream()
                .filter(r -> categoria.equalsIgnoreCase(r.getCategoria()))
                .collect(java.util.stream.Collectors.toList());
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
        if (receita.getCategoria() != null) {
            novaReceita.setCategoria(receita.getCategoria());
        }

        return receitaRepository.save(novaReceita);
    }
    public ReceitaDTO receitaEspec(Long id){
        Receita receita = receitaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        ReceitaDTO dto = new ReceitaDTO(
            receita.getTitulo(),
            receita.getDescricao(),
            receita.getImagem(),
            receita.getCategoria(),
            receita.getIngredientes(),
            receita.getPassos(),
            receita.getCreatedAt()
        );
        return dto;
    }

    public String uploadImagem(Long id, MultipartFile file) throws IOException {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = id + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        String imageUrl = "/receitas/uploads/" + filename;
        receita.setImagem(imageUrl);
        receitaRepository.save(receita);

        return imageUrl;
    }

    public Path getUploadPath() {
        return Paths.get(uploadDir);
    }
}
