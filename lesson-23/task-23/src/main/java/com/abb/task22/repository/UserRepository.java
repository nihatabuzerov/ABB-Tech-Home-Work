package com.abb.task22.repository;

import com.abb.task22.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE User u SET u.balance = u.balance - :amount WHERE u.id = :userId")
    void decreaseBalance(@Param("userId") UUID userId, @Param("amount") BigDecimal amount);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE User u SET u.balance = u.balance + :amount WHERE u.id = :userId")
    void increaseBalance(@Param("userId") UUID userId, @Param("amount") BigDecimal amount);
}
