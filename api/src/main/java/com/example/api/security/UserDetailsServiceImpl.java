package com.example.api.security;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.api.repository.UserRepository;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{
    
    private final UserRepository userRepository;

    
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        return userRepository.findByUsername(nome)
        .map(UserAuthenticated::new)
        .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
    
}
