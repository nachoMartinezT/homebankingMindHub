package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;
    private String name;
    private double maxAmount;
    @ElementCollection
    private Set<Integer> payments = new HashSet<>();

    @OneToMany(mappedBy = "loan",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, double maxAmount, Set<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public void setPayments(Set<Integer> payments) {
        this.payments = payments;
    }

    public Set<Client> getClient(){
        return clientLoans.stream().map(client -> client.getClient()).collect(Collectors.toSet());
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        this.clientLoans.add(clientLoan);
    }
}
