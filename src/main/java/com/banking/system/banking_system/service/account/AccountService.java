package com.banking.system.banking_system.service.account;

import com.banking.system.banking_system.accounts.entity.Account;
import com.banking.system.banking_system.accounts.repository.AccountRepository;
import com.banking.system.banking_system.dto.ApiResponse;
import com.banking.system.banking_system.dto.CreateAccountRequest;
import com.banking.system.banking_system.users.entity.Users;
import com.banking.system.banking_system.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository ;
    @Autowired
    private UserRepository userRepository ;

    private String generateAccountNumber() {
        return String.valueOf(System.currentTimeMillis());
    }
    public ApiResponse<Account> CreateAccount (CreateAccountRequest createAccountRequest)
    {
        Users user = (Users) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (userRepository.findById(user.getId()).isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "The User Is Not Found") ;
        }
        Users users = userRepository.findById(user.getId()).get()  ;
        Account account = Account.builder()
                .user(users)
                .accountNumber(generateAccountNumber())
                .accountType(createAccountRequest.getAccountType())
                .balance(BigDecimal.ZERO)
                .build() ;
        accountRepository.save(account) ;
        return ApiResponse.<Account>builder()
                .data(account)
                .success(true)
                .message("The Account is Created")
                .build();
    }

    public ApiResponse<List<Account>> GetAccountsByIDUSER ()
    {
        Users user = (Users) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if(userRepository.findById(user.getId()).isEmpty())
        {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "User Isn't Found") ;
        }
        List<Account>accountList = userRepository.findById(user.getId()).get().getAccountList() ;
        return ApiResponse.<List<Account>>builder()
                .message("Succes Message")
                .success(true)
                .data(accountList)
                .build();
    }

    public ApiResponse<Map<String, Object>> CheckBalance (Long id)
    {
        if (accountRepository.findById(id).isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The Account Not Exixts") ;
        }
        Account account = accountRepository.findById(id).get() ;
        Map <String ,Object> response = new HashMap<>() ;
        response.put("Balance",account.getBalance()) ;
        response.put("accountType" ,account.getAccountType())  ;
        return  ApiResponse.<Map<String,Object>>builder()
                .data(response)
                .success(true)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
