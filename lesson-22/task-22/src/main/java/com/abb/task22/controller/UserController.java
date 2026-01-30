package com.abb.task22.controller;

import com.abb.task22.domain.User;
import com.abb.task22.dto.BalanceRequest;
import com.abb.task22.dto.UserRequest;
import com.abb.task22.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UUID>
    createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<User>>
    getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User>
    getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PatchMapping("/{id}/decrease-balance")
    public ResponseEntity<User>
    decreaseBalance(@PathVariable UUID id, @Valid @RequestBody BalanceRequest request) {
        userService.decreaseBalance(id, request.getAmount());
        return ResponseEntity.ok(userService.getById(id));
    }

    @PatchMapping("/{id}/increase-balance")
    public ResponseEntity<User>
    increaseBalance(@PathVariable UUID id, @Valid @RequestBody BalanceRequest request) {
        userService.increaseBalance(id, request.getAmount());
        return ResponseEntity.ok(userService.getById(id));
    }
}
