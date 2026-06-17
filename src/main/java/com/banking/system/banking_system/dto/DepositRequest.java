package com.banking.system.banking_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositRequest {
    @NotBlank (message = "The Field Not TO be Empty")
    private  String AccountNumber ;
    @Positive(message = "The Amount Must be > 0 ")
    private BigDecimal Amount ;

}
