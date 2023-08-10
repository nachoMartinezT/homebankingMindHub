package com.mindhub.homebanking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {

    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;

    private List<TransactionDto> transactionsDto = new ArrayList<>();

    public AccountDto() {}

    public AccountDto(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactionsDto =  convertSetToDtoSet(account.getTransactions());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public List<TransactionDto> convertSetToDtoSet(List<Transaction> transactions){
        return transactions.stream()
                .map(TransactionDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                ", transactionsDto=" + transactionsDto +
                '}';
    }
}
