package com.banking.system.banking_system.users.entity;

import com.banking.system.banking_system.accounts.entity.Account;
import com.banking.system.banking_system.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column
    private String firstName ;
    @Column
    private String lastName ;
    @Column (unique = true ,nullable = false)
    private String email ;
    @Column
    private  String password ;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role ;
    private LocalDateTime createdAt ;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private  List<Account> accountList = new ArrayList<>()  ;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
