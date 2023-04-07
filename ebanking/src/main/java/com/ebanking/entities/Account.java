package com.ebanking.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "account")
@Data
public class Account implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "accountNumber")
    private Long accountNumber;
    @Column(name = "fname")
    private String fname;
    @Column(name = "lname")
    private String lname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "zip")
    private String zip;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "passport")
    private String passport;
    private double amount;

}
