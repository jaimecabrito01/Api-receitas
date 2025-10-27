package com.example.api.security;


import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;


import com.nimbusds.jose.jwk.source.ImmutableSecret;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
private static final String SECRET = "jK8xPq3nR5tU2vZ9yB1mD4sF7gH6kL0aN8eQ2rW5tY7uI1oP3zC6xV9bM4nJ";

    

    @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
      .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            // Endpoints públicos
            .requestMatchers("/authenticate").permitAll()
            .requestMatchers("/user/create").permitAll()
            .requestMatchers("/receitas/all").permitAll()

            // Endpoints que exigem autenticação
            .requestMatchers("/receitas/add").authenticated()
            .requestMatchers("/user/**").authenticated()

            // Qualquer outro endpoint exige login também
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults())
        .oauth2ResourceServer(conf -> conf.jwt(Customizer.withDefaults()));
            
                
            

        return http.build();
    }
    @Bean
    JwtDecoder jwtDecoder(){
      SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean 
    JwtEncoder jwtEncoder(){
        SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
