package com.banking.system.banking_system.controller.transaction;

import com.banking.system.banking_system.accounts.entity.Account;
import com.banking.system.banking_system.dto.ApiResponse;
import com.banking.system.banking_system.dto.DepositRequest;
import com.banking.system.banking_system.dto.TransferRequest;
import com.banking.system.banking_system.dto.WithdrawRequest;
import com.banking.system.banking_system.service.transaction.TransactionsService;
import com.banking.system.banking_system.transaction.entity.Transaction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionsService transactionsService ;
    @PostMapping ("/transfer")
    public ApiResponse<Transaction> Transfer (@Valid @RequestBody TransferRequest transferRequest)
    {
        return  transactionsService.Transfer(transferRequest) ;
    }

    @PostMapping ("/deposit")
    public ApiResponse<Account> Deposit (@Valid @RequestBody DepositRequest request)
    {
        return  transactionsService.Deposit(request) ;
    }

    @PostMapping ("/withdraw")
    public ApiResponse<Account> Deposit (@Valid @RequestBody WithdrawRequest request)
    {
        return  transactionsService.Withdraw(request) ;
    }

    @GetMapping ("/history/{id}")
    public ApiResponse<List<Transaction>> Deposit (@PathVariable Long id)
    {
        return  transactionsService.TransactionHistory(id) ;
    }

}
