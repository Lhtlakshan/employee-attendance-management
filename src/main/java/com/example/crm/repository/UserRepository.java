package com.example.crm.repository;

import com.example.crm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity , Integer> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String username);
}
