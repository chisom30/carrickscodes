package com.ebanking.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
public class Payment implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "payType")
    private String payType;
    @Column(name = "payAmt")
    private double payAmt;
    @Column(name = "payDate")
    private LocalDateTime payDate;
    @Column(name = "status")
    private boolean status = false;
    @OneToOne
    private Transaction transaction;
    @ManyToOne
    private Users users;


    @PrePersist
    public void preSave() {
        payDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        payDate = LocalDateTime.now();
    }
}
