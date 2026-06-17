package com.banking.system.banking_system.dto;

import com.banking.system.banking_system.enums.AccountType;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    private AccountType accountType ;
}
