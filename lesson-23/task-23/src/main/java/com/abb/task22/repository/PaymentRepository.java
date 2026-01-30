package com.abb.task22.repository;

import com.abb.task22.domain.Payment;
import com.abb.task22.domain.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findAllByOrderByCreatedAtDesc();

    List<Payment> findByUserIdOrderByCreatedAtDesc(UUID userId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Payment p SET p.status = :status WHERE p.id = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") PaymentStatus status);
}
