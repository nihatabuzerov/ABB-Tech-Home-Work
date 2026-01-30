package com.abb.task22.repository;

import com.abb.task22.domain.Payment;
import com.abb.task22.domain.PaymentStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Payment> rowMapper = (rs, _) -> {
        
        Payment payment = new Payment();
        
        payment.setId(rs.getObject("id", UUID.class));
        payment.setUserId(rs.getObject("user_id", UUID.class));
        payment.setAmount(rs.getBigDecimal("amount"));
        payment.setStatus(PaymentStatus.valueOf(rs.getString("status")));
        payment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        
        return payment;
    };
    
    public PaymentRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<Payment> findAll () {
        String sql = "SELECT * FROM payments ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }
    
    public Optional<Payment> findById (UUID id) {
        String sql = "SELECT * FROM payments WHERE id = ?";
        List<Payment> payments = jdbcTemplate.query(sql, rowMapper, id);
        return payments.isEmpty() ? Optional.empty() : Optional.of(payments.getFirst());
    }
    
    public List<Payment> findByUserId (UUID userId) {
        String sql = "SELECT * FROM payments WHERE user_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }
    
    public UUID save (Payment payment) {
        String sql = "INSERT INTO payments " +
                     "(user_id, amount, status, created_at) " +
                     "VALUES (?, ?, ?, ?) RETURNING id";
        
        return jdbcTemplate.queryForObject(
                sql,
                UUID.class,
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus().name(),
                Timestamp.valueOf(payment.getCreatedAt())
        );
    }
    
    public void updateStatus (UUID id, PaymentStatus status) {
        String sql = "UPDATE payments SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status.name(), id);
    }
}
