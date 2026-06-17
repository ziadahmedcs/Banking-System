package com.banking.system.banking_system.controller.account;

import com.banking.system.banking_system.accounts.entity.Account;
import com.banking.system.banking_system.dto.ApiResponse;
import com.banking.system.banking_system.dto.CreateAccountRequest;
import com.banking.system.banking_system.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService ;
    @Operation(summary = "Create Bank Account")
    @PostMapping("/account")
    public ApiResponse<Account> CreateAccount (@RequestBody CreateAccountRequest createAccountRequest)
    {
        return  accountService.CreateAccount(createAccountRequest) ;
    }
    @Operation(summary = "Get List Of Accounts")
    @GetMapping("/account")
    public  ApiResponse<List<Account>> GetAccountByIDUSSER ()
    {
        return  accountService.GetAccountsByIDUSER() ;
    }
    @Operation(summary = "Check Money")
    @GetMapping("/account/{id}/check")
    public  ApiResponse<Map<String,Object>> CheckBalance (@PathVariable Long id)
    {
        return  accountService.CheckBalance(id) ;
    }


}
