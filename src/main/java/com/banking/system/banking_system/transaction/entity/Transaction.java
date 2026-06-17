package com.banking.system.banking_system.transaction.entity;

import com.banking.system.banking_system.accounts.entity.Account;
import com.banking.system.banking_system.enums.TransactionStatus;
import com.banking.system.banking_system.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id ;
    private BigDecimal amount ;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType ;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus ;
    private  String  description ;
    private LocalDateTime transaction_date ;
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private  Account fromAccount  ;
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private  Account toAccount  ;
    @PrePersist
    public void onCreated ()
    {
        this.transaction_date = LocalDateTime.now() ;
    }
}
