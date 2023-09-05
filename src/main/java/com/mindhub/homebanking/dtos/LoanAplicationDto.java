package com.mindhub.homebanking.dtos;

import javax.persistence.ElementCollection;
import java.util.HashSet;
import java.util.Set;

public class LoanAplicationDto {

    private Long id;
    private Double amount;
    @ElementCollection
    private Set<Integer> payments = new HashSet<>();
    private String account;


    public LoanAplicationDto() {
    }

    public LoanAplicationDto(Long id, Double amount, Set<Integer> payments, String account) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public String getAccount() {
        return account;
    }
}
