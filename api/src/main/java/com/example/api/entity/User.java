package com.example.api.entity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.ManyToAny;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.api.dto.LoginRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name= "users")
@Getter
@Setter
@NoArgsConstructor  
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="user_id",columnDefinition = "uuid")
    private UUID id;
    @Column(unique = true)
    private String nome;
    private String email;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable( name = "tb_users_roles",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles;
    private String login;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Receita> receitas;
  

  

    public boolean isLoginCorrect(LoginRequest loginRequest,PasswordEncoder passwordEncoder){
       return  passwordEncoder.matches(loginRequest.password(), this.password);
       
    }

    

    
}
  
   
