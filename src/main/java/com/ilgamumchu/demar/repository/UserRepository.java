package com.ilgamumchu.demar.repository;

import com.ilgamumchu.demar.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> existsByEmail(String email);

    Optional<User> findUserById(Long id);
}
