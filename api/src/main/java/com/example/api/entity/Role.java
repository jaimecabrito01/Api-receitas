package com.example.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long Id;

    private String nome;
     public enum Values{
        basic(2l),
        ADMIN(1l);

        Long roleid;

        Values(Long roleid){
            this.roleid = roleid;
        }
        public Long getRole(){
            return roleid;
        }
    }

}
