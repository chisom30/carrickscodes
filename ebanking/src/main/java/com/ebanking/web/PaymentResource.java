package com.ebanking.web;

import com.ebanking.dto.PaymentDto;
import com.ebanking.dto.TransactionDto;
import com.ebanking.dto.UsersDto;
import com.ebanking.entities.*;
import com.ebanking.entities.repositories.PaymentRepository;
import com.ebanking.entities.repositories.TransactionRepository;
import com.ebanking.entities.repositories.UsersRepository;
import com.ebanking.util.ResourceException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@AllArgsConstructor
@Controller
@CrossOrigin
public class PaymentResource {
    private final PaymentRepository paymentRepository;
    private final UsersRepository usersRepository;
    private final TransactionRepository transactionRepository;

    @PostMapping("/save-payment")
    public ResponseEntity savePayment(@Valid @RequestBody PaymentDto paymentDto,@RequestHeader("Authorization") String Authorization) throws ResourceException {
        Payment payment = new Payment();
        Users users =  this.usersRepository.findById(paymentDto.getUserId()).orElseThrow(() -> {
            return new ResourceException("User Id not found");
        });
        payment.setUsers(users);
        Transaction transaction = this.transactionRepository.findById(paymentDto.getTransactionId()).orElseThrow(() -> {
            return new ResourceException("Transaction Id not found");
        });
        payment.setTransaction(transaction);
        payment.setPayType(transaction.getTransType().toString());
        payment.setPayAmt(transaction.getTransAmt());
        payment.setPayDate(transaction.getTransDate());
        this.paymentRepository.save(payment);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/view-payment")
    public ResponseEntity<List<PaymentDto>> getPayment(@RequestHeader("Authorization") String Authorization) {
        List<Payment> paymentList = this.paymentRepository.findAll();
        List<PaymentDto> paymentDtos = new ArrayList<>();
        paymentList.forEach(payment -> {
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setPayType(payment.getPayType());
            paymentDto.setPayAmt(payment.getPayAmt());
            paymentDto.setPayDate(payment.getPayDate());
            paymentDto.setUserId(payment.getUsers().getId());
            paymentDto.setTransactionId(payment.getTransaction().getId());
            paymentDtos.add(paymentDto);
        });
        return ResponseEntity.ok(paymentDtos);
    }
}
