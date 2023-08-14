package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDto {
    private Long id;
    private Long idLoan;
    private String name;
    private double amount;
    private int payments;

    private ClientLoanDto(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.idLoan = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getLoan().getMaxAmount();
        this.payments = clientLoan.getPayments().get(0);
    }

}
