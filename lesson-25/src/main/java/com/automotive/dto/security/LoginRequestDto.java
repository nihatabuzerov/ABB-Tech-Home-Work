package com.automotive.dto.security;

public record LoginRequestDto(
        String username,
        String password
) {}
