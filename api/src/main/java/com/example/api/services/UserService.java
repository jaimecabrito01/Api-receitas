package com.example.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.dto.UserCreateDTO;
import com.example.api.entity.User;
import com.example.api.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

       public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(UserCreateDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setNome(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(user);
    }

    public List<User> list() {
        return userRepository.findAll();

    }

    public void delete(Long dto) {

        userRepository.deleteById(dto);
    }
    public void update(UserCreateDTO dto,Long id){
       User newUser =  userRepository.findById(id).orElseThrow(()-> 
       new RuntimeException("usuario nao encontrado"));
       
       newUser.setEmail(dto.getEmail());
       newUser.setNome(dto.getName());
       newUser.setPassword(dto.getPassword());

        userRepository.save(newUser);
       

    }

}
