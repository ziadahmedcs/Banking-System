package com.banking.system.banking_system.controller.auth;

import com.banking.system.banking_system.dto.ApiResponse;
import com.banking.system.banking_system.dto.LoginRequest;
import com.banking.system.banking_system.dto.RegisterRequest;
import com.banking.system.banking_system.dto.RestPasswordRequest;
import com.banking.system.banking_system.service.auth.AuthService;
import com.banking.system.banking_system.users.entity.Users;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService ;
    @PostMapping("/register")
    public ApiResponse<Users> register (@Valid @RequestBody RegisterRequest registerRequest)
    {
        return authService.Register(registerRequest) ;
    }

    @PostMapping("/login")
    public ApiResponse<Map> login ( @Valid @RequestBody LoginRequest loginRequest)
    {
        return authService.Login(loginRequest) ;
    }

    @PostMapping("/rest")
    public ApiResponse<Map<String,Object>> rest ( @Valid @RequestBody RestPasswordRequest restPasswordRequest)
    {
        return authService.RestPassword(restPasswordRequest) ;
    }

    @GetMapping("/me")
    public ApiResponse<Users> me ()
    {
        return authService.GetMe() ;
    }
}
