package com.example.api.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.api.dto.UserCreateDTO;
import com.example.api.entity.Role;
import com.example.api.entity.User;
import com.example.api.repository.RoleRepository;
import com.example.api.repository.UserRepository;

@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
   


    UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passwordEncoder = bCryptPasswordEncoder;
    }


     
    public User create(UserCreateDTO dto) {
        User user = new User();
        var role = roleRepository.findByNome(Role.Values.basic.name());
        user.setEmail(dto.getEmail());
        user.setNome(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(role));

        return userRepository.save(user);
    }

    public List<User> list() {
        return userRepository.findAll();

    }

    public void delete(JwtAuthenticationToken jwt) {

        userRepository.deleteById(UUID.fromString(jwt.getName()));
    }
    public void update(UserCreateDTO dto,JwtAuthenticationToken jwt){
       User newUser =  userRepository.findById(UUID.fromString(jwt.getName())).orElseThrow(()-> 
       new RuntimeException("usuario nao encontrado"));
       
       newUser.setEmail(dto.getEmail());
       newUser.setNome(dto.getName());
       newUser.setPassword(dto.getPassword());

        userRepository.save(newUser);
       

    }

}
