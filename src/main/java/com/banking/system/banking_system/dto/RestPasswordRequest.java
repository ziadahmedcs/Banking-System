package com.banking.system.banking_system.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestPasswordRequest {
    private  Long id ;
    @NotBlank(message = "New Password must not be empty")
    @Size(min = 6, max = 15, message = "New Password must be between 6 and 15 characters")
    private  String new_password ;
    @NotBlank(message = "Confirm Password must not be empty")
    @Size(min = 6, max = 15, message = "Confirm Password must be between 6 and 15 characters")
    private  String confirm_password ;
}
