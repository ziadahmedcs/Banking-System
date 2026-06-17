package com.banking.system.banking_system.dto;

import jakarta.persistence.PrePersist;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private boolean success;

    private String message;

    private T data;

    private LocalDateTime timestamp;


    @PrePersist
    public void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}