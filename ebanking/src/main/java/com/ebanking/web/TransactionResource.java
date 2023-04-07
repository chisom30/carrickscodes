package com.ebanking.web;

import com.ebanking.dto.TransactionDto;
import com.ebanking.dto.TransactionType;
import com.ebanking.entities.Account;
import com.ebanking.entities.Payment;
import com.ebanking.entities.Transaction;
import com.ebanking.entities.Users;
import com.ebanking.entities.repositories.AccountRepository;
import com.ebanking.entities.repositories.PaymentRepository;
import com.ebanking.entities.repositories.TransactionRepository;
import com.ebanking.entities.repositories.UsersRepository;
import com.ebanking.util.ResourceException;
import com.ebanking.util.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestMapping("/api")
@CrossOrigin
@Controller
@AllArgsConstructor
public class TransactionResource {
    private final TransactionRepository transactionRepository;
    private final UsersRepository usersRepository;
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    @PostMapping("/saveTransaction")
    public ResponseEntity<ResponseMessage> saveTransaction(@Valid @RequestBody TransactionDto transactionDto,@RequestHeader("Authorization") String Authorization) throws ResourceException {
        double bal = 0;
        double min = 1000;
        Account account = this.accountRepository.findByAccountNumber(transactionDto.getAccountNumber());
        Transaction transaction = new Transaction();
        Payment payment = new Payment();
        ResponseMessage responseMessage = new ResponseMessage();

        transaction.setAccount(account);
        if (Objects.equals(transactionDto.getTransType(), TransactionType.WITHDRAWAL)) {
            if (transactionDto.getTransAmt() <= min) {
                //throw new ResourceException("Insufficient Balance");
                responseMessage.setMessage("Insufficient Balance");
                return ResponseEntity.ok(responseMessage);
            }
            if (transactionDto.getTransAmt() >= account.getAmount()) {
                //  throw new ResourceException("Insufficient Balance");
                responseMessage.setMessage("Insufficient Balance");
                return ResponseEntity.ok(responseMessage);
            }
            if (transactionDto.getTransAmt() < account.getAmount() && transactionDto.getTransAmt() > min) {
                transaction.setTransAmt(transactionDto.getTransAmt());
                bal = account.getAmount() - transactionDto.getTransAmt();
                System.out.println("acountAmount: " + account.getAmount());
                System.out.println("Transaction: " + transactionDto.getTransAmt());
                System.out.println("Bal: " + bal);
                transaction.setBalance(bal);
                transaction.setTransType(transactionDto.getTransType());
                account.setAmount(bal);

                this.accountRepository.save(account);
            }

        }
        if (Objects.equals(transactionDto.getTransType(), TransactionType.DEPOSIT)) {
            transaction.setTransAmt(transactionDto.getTransAmt());
            bal = (account.getAmount() + transactionDto.getTransAmt());
            transaction.setBalance(bal);
            transaction.setTransType(transactionDto.getTransType());
            account.setAmount(bal);
            this.accountRepository.save(account);
        }
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        //transaction.setTransDate(LocalDate.parse(transactionDto.getTransDate(),formatter));
        //   transaction.setTransDate(LocalDateTime.now());
        Transaction transaction1 = this.transactionRepository.save(transaction);
        payment.setTransaction(transaction1);
        payment.setPayType(transaction1.getTransType().toString());
        payment.setPayAmt(transaction1.getTransAmt());
        //  payment.setPayDate(LocalDateTime.now());
        payment.setStatus(Boolean.TRUE);
        this.paymentRepository.save(payment);
        responseMessage.setTransaction(transaction1);
        return ResponseEntity.ok(responseMessage);
    }

    @PutMapping("/update-transaction")
    public ResponseEntity updateTransaction(@Valid @RequestBody TransactionDto transactionDto,@RequestHeader("Authorization") String Authorization) {
        Transaction transaction = new Transaction();
        Users users = (Users) this.usersRepository.findById(transactionDto.getUserId()).get();
        transaction.setUsers(users);
        String withdraw = "";
        String deposit = "";
        double bal = 0;
        double min = 1000;
        transaction.setTransAmt(transactionDto.getTransAmt());
        transaction.setMinBalance(min);
        if (transactionDto.getTransType().equals(TransactionType.WITHDRAWAL)) {
            transaction.setTransAmt(transactionDto.getTransAmt());
            bal = (transactionDto.getBalance() - transactionDto.getTransAmt());
        } else if (transactionDto.getTransType().equals(TransactionType.DEPOSIT)) {
            transaction.setTransAmt(transactionDto.getTransAmt());
            bal = (transactionDto.getBalance() + transactionDto.getTransAmt());
        }
        transaction.setBalance(bal);
        // transaction.setTransDate(LocalDateTime.now());
        this.transactionRepository.save(transaction);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/view-transaction")
    public ResponseEntity<List<TransactionDto>> getTransaction(@RequestHeader("Authorization") String Authorization) {
        List<Transaction> transactionList = this.transactionRepository.findAll();
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactionList.forEach(transaction -> {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTransType(transaction.getTransType());
            transactionDto.setTransAmt(transaction.getTransAmt());
            transactionDto.setBalance(transaction.getBalance());
            transactionDto.setMinBalance(transaction.getMinBalance());
            transactionDto.setTransDate(String.valueOf(transaction.getTransDate()));
            transactionDto.setUserId(transaction.getUsers().getId());
            transactionDtos.add(transactionDto);
        });
        return ResponseEntity.ok(transactionDtos);
    }

    @GetMapping({"/get-transaction-by-id{id}"})
    public ResponseEntity<Transaction> getTransactionId(@PathVariable Long id,@RequestHeader("Authorization") String Authorization) throws ResourceException {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceException("Transaction ID not found"));
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping({"/delete-transaction/{id}"})
    public void delete(@PathVariable Long id,@RequestHeader("Authorization") String Authorization) {
        this.transactionRepository.deleteById(id);
    }
}
