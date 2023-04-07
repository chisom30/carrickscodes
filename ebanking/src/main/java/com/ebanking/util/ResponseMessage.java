package com.ebanking.util;

import com.ebanking.entities.Transaction;
import lombok.Data;

@Data
public class ResponseMessage {
    private String message;
    private Transaction transaction;
}
