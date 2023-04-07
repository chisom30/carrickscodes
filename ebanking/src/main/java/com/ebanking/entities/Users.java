package com.ebanking.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
public class Users implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "username")
    private String username;
    @Column (name = "password")
    private String password;
    @OneToOne
    private Account account;
    @OneToOne
    private Role role;
}
