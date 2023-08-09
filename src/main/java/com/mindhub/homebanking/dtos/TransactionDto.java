package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;

public class TransactionDto {
    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDate dateOfTransaction;
}
