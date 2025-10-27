package com.example.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long Id;

    private String nome;
     public enum Values{
        BASIC(2l),
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
