package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    private Loan loan;

    @ElementCollection
    private List<Integer> payments = new ArrayList<>();

    public ClientLoan(){
    }
    public ClientLoan(List<Integer> payments, Client client, Loan loan) {
        this.payments = payments;
        this.client = client;
        this.loan = loan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
