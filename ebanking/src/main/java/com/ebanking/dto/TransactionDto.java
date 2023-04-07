package com.ebanking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class TransactionDto {
    private Long id;
    private TransactionType transType;
    private Long AccountNumber;
    private double transAmt;
    @JsonIgnore
    private double balance;
    @JsonIgnore
    private double minBalance;
    @JsonIgnore
    private String transDate;
    @JsonIgnore
    private Long userId;
}
