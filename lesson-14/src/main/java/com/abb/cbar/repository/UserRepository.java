package com.abb.cbar.repository;

import com.abb.cbar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername (String username);
    
    boolean existsByUsername (String username);
}
