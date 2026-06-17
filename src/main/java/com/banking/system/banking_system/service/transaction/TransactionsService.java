package com.banking.system.banking_system.service.transaction;

import com.banking.system.banking_system.accounts.entity.Account;
import com.banking.system.banking_system.accounts.repository.AccountRepository;
import com.banking.system.banking_system.dto.ApiResponse;
import com.banking.system.banking_system.dto.DepositRequest;
import com.banking.system.banking_system.dto.TransferRequest;
import com.banking.system.banking_system.dto.WithdrawRequest;
import com.banking.system.banking_system.enums.TransactionStatus;
import com.banking.system.banking_system.enums.TransactionType;
import com.banking.system.banking_system.transaction.entity.Transaction;
import com.banking.system.banking_system.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionsService {
    @Autowired
    private AccountRepository accountRepository ;
    @Autowired
    private TransactionRepository transactionRepository ;
    public ApiResponse<Transaction> Transfer (TransferRequest transferRequest)
    {

        if (accountRepository.findByAccountNumber(transferRequest.getFromaccount()).isEmpty())
        {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND ,"Source Account Not Found") ;
        }
        if(accountRepository.findByAccountNumber(transferRequest.getToaccount()).isEmpty())
        {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND ,"Destination Account Not Found") ;
        }
        Account fromaccount = accountRepository.findByAccountNumber(transferRequest.getFromaccount()).get() ;
        Account toaccount = accountRepository.findByAccountNumber(transferRequest.getToaccount()).get() ;
        if (fromaccount.getBalance().compareTo(transferRequest.getAmount())<0)
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Insufficient Balance");
        }
        fromaccount.setBalance(fromaccount.getBalance().subtract(transferRequest.getAmount())); ;
        toaccount.setBalance(toaccount.getBalance().add(transferRequest.getAmount())); ;
        accountRepository.save(fromaccount) ;
        accountRepository.save(toaccount) ;
        Transaction transaction  = Transaction.builder()
                .transaction_date(LocalDateTime.now())
                .transactionType(TransactionType.TRANSFER)
                .transactionStatus(TransactionStatus.SUCCESS)
                .amount(transferRequest.getAmount())
                .fromAccount(fromaccount)
                .toAccount(toaccount)
                .description("Transfer Money:-"+transferRequest.getAmount()+ "From Acoount :-" + fromaccount + " to" + toaccount)
                .build();
        return ApiResponse.<Transaction>builder().data(transaction).success(true).build();

    }

    @Transactional
    public  ApiResponse<Account> Deposit (DepositRequest request)
    {
        if (accountRepository.findByAccountNumber(request.getAccountNumber()).isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "The Account Not Exists") ;
        }
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber()).get() ;
        account.setBalance(
                account.getBalance().add(request.getAmount())
        );
        accountRepository.save(account) ;
        Transaction transaction = Transaction.builder()
                .description("Deposit Succes")
                .transaction_date(LocalDateTime.now())
                .transactionType(TransactionType.DEPOSIT)
                .transactionStatus(TransactionStatus.SUCCESS)
                .toAccount(account)
                .amount(request.getAmount())
                .build();
        transactionRepository.save(transaction) ;
        return ApiResponse.<Account>builder()
                .success(true)
                .data(account)
                .message("Success Deposit")
                .build();
    }
    @Transactional
    public  ApiResponse<Account> Withdraw (WithdrawRequest request)
    {
        if (accountRepository.findByAccountNumber(request.getAccountNumber()).isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "The Account Not Exists") ;
        }
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber()).get() ;
        account.setBalance(
                account.getBalance().subtract(request.getAmount())
        );
        accountRepository.save(account) ;
        Transaction transaction = Transaction.builder()
                .description("WithDraw Succes")
                .transaction_date(LocalDateTime.now())
                .transactionType(TransactionType.WITHDRAW)
                .transactionStatus(TransactionStatus.SUCCESS)
                .fromAccount(account)
                .amount(request.getAmount())
                .build();
        transactionRepository.save(transaction) ;
        return ApiResponse.<Account>builder()
                .success(true)
                .data(account)
                .message("Success Withdraw")
                .build();
    }
    public  ApiResponse<List<Transaction>> TransactionHistory (Long accountid)
    {
        if (accountRepository.findById(accountid).isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "The Account Not Exists") ;
        }
        Account account = accountRepository.findById(accountid).get() ;
        List<Transaction> transactions = transactionRepository.findByFromAccountIdOrToAccountId(accountid ,accountid) ;
        return ApiResponse.<List<Transaction>>builder()
                .data(transactions)
                .success(true)
                .build();
    }
}
