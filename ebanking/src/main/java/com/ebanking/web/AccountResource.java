package com.ebanking.web;

import com.ebanking.dto.AccountDto;
import com.ebanking.entities.Account;
import com.ebanking.entities.repositories.AccountRepository;
//import com.ebanking.util.AccountNum;
import com.ebanking.util.AccountNum;
import com.ebanking.util.ResourceException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
@Controller
public class AccountResource {
    private final AccountRepository accountRepository;


    @PostMapping("/save-account")
    public ResponseEntity saveAccount(@Valid @RequestBody AccountDto accountDto, @RequestHeader("Authorization") String Authorization) {
        Account account = new Account();
        ;
        //  AccountNum accountNum = new AccountNum();
        account.setAccountNumber((long) this.getRndNumber());
        account.setFname(accountDto.getFname());
        account.setLname(accountDto.getLname());
        account.setEmail(accountDto.getEmail());
        account.setPhone(accountDto.getPhone());
        account.setAddress(accountDto.getAddress());
        account.setCity(accountDto.getCity());
        account.setState(accountDto.getState());
        account.setZip(accountDto.getZip());
        account.setPassport(accountDto.getPassport());
        account.setAmount(accountDto.getAmount());
        this.accountRepository.save(account);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-account")
    public ResponseEntity updateAccount(@Valid @RequestBody AccountDto accountDto, @RequestHeader("Authorization") String Authorization) throws ResourceException {
        Account account = accountRepository.findById(accountDto.getId()).orElseThrow(() -> new ResourceException("Account ID not found"));
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setFname(accountDto.getFname());
        account.setLname(accountDto.getLname());
        account.setEmail(accountDto.getEmail());
        account.setPhone(accountDto.getPhone());
        account.setAddress(accountDto.getAddress());
        account.setCity(accountDto.getCity());
        account.setState(accountDto.getState());
        account.setZip(accountDto.getZip());
        account.setPassport(accountDto.getPassport());
        this.accountRepository.save(account);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/view-account")
    public ResponseEntity<List<AccountDto>> viewAccount(@RequestHeader("Authorization") String Authorization) {
        List<Account> accountList = this.accountRepository.findAll();
        List<AccountDto> accountDtos = new ArrayList();
        accountList.stream().map((account) -> {
            AccountDto accountDto = new AccountDto();
            accountDto.setAccountNumber(account.getAccountNumber());
            accountDto.setFname(account.getFname());
            accountDto.setLname(account.getLname());
            accountDto.setEmail(account.getEmail());
            accountDto.setPhone(account.getPhone());
            accountDto.setAddress(account.getAddress());
            accountDto.setCity(account.getCity());
            accountDto.setState(account.getState());
            accountDto.setZip(account.getZip());
            accountDto.setPassport(account.getPassport());
            accountDto.setAmount(account.getAmount());
            accountDtos.add(accountDto);
            return accountDtos;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(accountDtos);
    }

    @GetMapping({"/get-account-by-id{id}"})
    public ResponseEntity<Account> getAccountId(@PathVariable Long id, @RequestHeader("Authorization") String Authorization) throws ResourceException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceException("Account ID not found"));
        return ResponseEntity.ok(account);
    }

    @DeleteMapping({"/delete-account/{id}"})
    public void delete(@PathVariable Long id, @RequestHeader("Authorization") String Authorization) {
        this.accountRepository.deleteById(id);
    }

    public int getRndNumber() {
        Random random = new Random();
        int randomNumber = 0;
        boolean loop = true;
        while (loop) {
            randomNumber = random.nextInt();
            if (Integer.toString(randomNumber).length() == 10 && !Integer.toString(randomNumber).startsWith("-")) {
                loop = false;
            }
        }
        return randomNumber;
    }
}
