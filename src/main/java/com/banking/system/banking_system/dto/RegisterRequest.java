package com.banking.system.banking_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "First name must not be empty")
    @Size(min = 3, max = 15, message = "First name must be between 3 and 15 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain letters only")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    @Size(min = 3, max = 15, message = "Last name must be between 3 and 15 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain letters only")

    private String lastName;

    @Email(message = "Must enter a valid email")
    @NotBlank(message = "Email must not be empty")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 6, max = 15, message = "Password must be between 6 and 15 characters")
    private String password;
}