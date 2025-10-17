package com.example.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.api.dto.UserCreateDTO;
import com.example.api.entity.User;
import com.example.api.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(UserCreateDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setNome(dto.getName());
        user.setPassword(dto.getPassword());

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
