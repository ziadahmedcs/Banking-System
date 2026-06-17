package com.banking.system.banking_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Email(message = "Must enter a valid email")
    @NotBlank(message = "Email must not be empty")
    private String email;
    @NotBlank(message = "Password must not be empty")
    @Size(min = 6, max = 15, message = "Password must be between 6 and 15 characters")
    private String password;
}
