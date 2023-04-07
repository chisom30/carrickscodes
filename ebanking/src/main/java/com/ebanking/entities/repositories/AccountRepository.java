package com.ebanking.entities.repositories;

import com.ebanking.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(Long accountNumber);
}
