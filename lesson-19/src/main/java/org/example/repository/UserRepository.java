package org.example.repository;


import org.example.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private static final Map<String, User> users = new HashMap<>();

    static {
        users.put("admin", new User(1L, "admin", "admin123"));
        users.put("user", new User(2L, "user", "user123"));
    }

    public static Optional<User> findByUsername (String username) {
        return Optional.ofNullable(users.get(username));
    }

    public static Optional<User> authenticate (
            String username,
            String password
    ) {
        return findByUsername(username).filter(
                user -> user.getPassword().equals(password)
        );
    }
}