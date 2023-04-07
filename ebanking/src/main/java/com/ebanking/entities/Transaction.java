package com.ebanking.entities;

import com.ebanking.dto.TransactionType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
public class Transaction implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transType")

    @Enumerated(EnumType.STRING)
    private TransactionType transType;

    @Column(name = "transAmt")
    private double transAmt;
    @Column(name = "balance")
    private double balance;
    @Column(name = "minBalance")
    private double minBalance;
    @Column(name = "transDate")
    private LocalDateTime transDate;
    @ManyToOne
    private Users users;
    @ManyToOne
    private Account account;

    @PrePersist
    public void preSave() {
        transDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        transDate = LocalDateTime.now();
    }
}
