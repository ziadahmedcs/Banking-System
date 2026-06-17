package com.banking.system.banking_system.accounts.entity;

import com.banking.system.banking_system.enums.AccountType;
import com.banking.system.banking_system.transaction.entity.Transaction;
import com.banking.system.banking_system.users.entity.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id ;
    @Column (unique = true)
    private  String accountNumber ;

    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private AccountType accountType ;

    private LocalDateTime createdAt ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
     private Users user ;
    @OneToMany(mappedBy = "fromAccount", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> sentTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "toAccount", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> receivedTransactions = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
