package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

import java.util.HashSet;
import java.util.Set;

public class ClientLoanDto {
    private Long id;
    private Long idLoan;
    private String name;
    private double amount;
    private Set<Integer> payments = new HashSet<>();

    public ClientLoanDto(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.idLoan = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getLoan().getMaxAmount();
        this.payments = clientLoan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public Long getIdLoan() {
        return idLoan;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }
}
