package com.ebanking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private Long id;
    private String payType;
    private double payAmt;
    @JsonIgnore
    private LocalDateTime payDate;
    private boolean status;
    private Long transactionId;
    private Long userId;
}
