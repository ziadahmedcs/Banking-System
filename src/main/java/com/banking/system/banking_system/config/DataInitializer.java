package com.banking.system.banking_system.config;

import com.banking.system.banking_system.enums.Role;
import com.banking.system.banking_system.users.entity.Users;
import com.banking.system.banking_system.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByEmail("admin@bank.com").isEmpty()) {

            Users users = Users.builder()
                    .email("admin@bank.com")
                    .role(Role.ADMIN)
                    .password(passwordEncoder.encode("admin123"))
                    .firstName("System")
                    .lastName("bank")
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(users);

            System.out.println("Admin Created Successfully");
        }
    }
}