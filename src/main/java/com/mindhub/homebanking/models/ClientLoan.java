package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Integer> payments = new HashSet<>();

    public ClientLoan(){
    }
    public ClientLoan(Set<Integer> payments) {
        this.payments = payments;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public void setPayments(Set<Integer> payments) {
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
