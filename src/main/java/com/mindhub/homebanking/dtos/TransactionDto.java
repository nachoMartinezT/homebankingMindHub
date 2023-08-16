package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDto {
    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime dateOfTransaction;



    public TransactionDto() {
    }

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.dateOfTransaction = transaction.getDateOfTransaction();
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateOfTransaction() {
        return dateOfTransaction;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", dateOfTransaction=" + dateOfTransaction +
                '}';
    }
}
