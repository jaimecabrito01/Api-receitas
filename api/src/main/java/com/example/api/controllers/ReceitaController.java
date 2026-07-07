package com.example.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.ReceitaDTO;
import com.example.api.entity.Receita;
import com.example.api.repository.ReceitaRepository;
import com.example.api.repository.UserRepository;
import com.example.api.services.ReceitaService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService service;
    @Autowired
    private ReceitaRepository receitaRepository;

    @GetMapping("/me")
    public ResponseEntity minhasReceitas(JwtAuthenticationToken token) {
        return ResponseEntity.ok(service.receitasUsuario(token));

    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity create(@RequestBody ReceitaDTO receita, JwtAuthenticationToken jwt) {

        return ResponseEntity.ok(service.create(receita, jwt));
    }

    @GetMapping("/all")
    public ResponseEntity list(@RequestParam(required = false) String categoria) {
        if (categoria != null && !categoria.isEmpty()) {
            return ResponseEntity.ok(service.listByCategoria(categoria));
        }
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity receita(@PathVariable Long id) {
            return ResponseEntity.ok(service.receitaEspec(id));

    }

    @PutMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity update(@PathVariable Long id, @RequestBody ReceitaDTO receita) {

        return ResponseEntity.ok(service.update(receita, id));

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity delete(@PathVariable Long id, JwtAuthenticationToken jwt) {
        service.delete(id, jwt);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/imagem")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity uploadImagem(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = service.uploadImagem(id, file);
            return ResponseEntity.ok(java.util.Map.of("imagem", imageUrl));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao fazer upload da imagem");
        }
    }

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<byte[]> servirImagem(@PathVariable String filename) {
        try {
            Path filePath = service.getUploadPath().resolve(filename);
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }
            byte[] imageBytes = Files.readAllBytes(filePath);
            String contentType = filename.endsWith(".png") ? "image/png" :
                                filename.endsWith(".jpg") || filename.endsWith(".jpeg") ? "image/jpeg" :
                                "image/webp";
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
