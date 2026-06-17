package com.banking.system.banking_system.users.repository;

import com.banking.system.banking_system.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email)  ;
    boolean existsByEmail(String email)  ;
}
