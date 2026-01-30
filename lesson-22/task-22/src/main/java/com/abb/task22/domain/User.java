package com.abb.task22.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class User {
 
    private UUID id;
 
    private String fullName;
 
    private BigDecimal balance;
}