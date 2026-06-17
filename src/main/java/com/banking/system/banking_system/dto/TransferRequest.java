package com.banking.system.banking_system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    @NotBlank(message = "FromAccount must not be empty")
    private String fromaccount ;
    @NotBlank(message = "ToAccount must not be empty")
    private  String toaccount ;
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount ;
}
