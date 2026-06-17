package com.banking.system.banking_system.transaction.repository;

import com.banking.system.banking_system.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction ,Long> {

    List<Transaction> findByFromAccountIdOrToAccountId(
            Long fromAccountId,
            Long toAccountId
    );
}
