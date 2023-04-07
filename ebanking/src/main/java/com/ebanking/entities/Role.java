package com.ebanking.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role")
@Data
public class Role implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "name")
    private String name;

}
