package com.abb.task22.repository;

import com.abb.task22.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<User> rowMapper = (rs, _) -> {
        
        User user = new User();
        
        user.setId(rs.getObject("id", UUID.class));
        user.setFullName(rs.getString("full_name"));
        user.setBalance(rs.getBigDecimal("balance"));
        
        return user;
    };
    
    public UserRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public UUID save (User user) {
        String sql = "INSERT INTO users (full_name, balance) VALUES (?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, UUID.class, user.getFullName(), user.getBalance());
    }
    
    public Optional<User> findById (UUID id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, rowMapper, id);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.getFirst());
    }
    
    public List<User> findAll () {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, rowMapper);
    }
    
    public boolean existsById (UUID id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
    
    public void decreaseBalance (UUID userId, BigDecimal amount) {
        String sql = "UPDATE users SET balance = balance - ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, userId);
    }
    
    public void increaseBalance (UUID userId, BigDecimal amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, userId);
    }
}
