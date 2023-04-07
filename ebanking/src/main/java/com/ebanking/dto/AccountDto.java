package com.ebanking.dto;

import lombok.Data;

@Data
public class AccountDto {
    private Long id;
    private Long accountNumber;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String zip;
    private String address;
    private String city;
    private String state;
    private String passport;
    private double amount;
}
