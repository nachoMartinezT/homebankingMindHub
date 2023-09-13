package com.mindhub.homebanking.dtos;

public class LoanAplicationDto {

    private Long loanTypeId;
    private Double amount;
    private int payments;
    private String toAccountNumber;


    public LoanAplicationDto() {
    }

    public LoanAplicationDto(Long loanTypeId, Double amount, int payments, String toAccountNumber) {
        this.loanTypeId = loanTypeId;
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }

    public Long getLoanTypeId() {
        return loanTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    @Override
    public String toString() {
        return "LoanAplicationDto{" +
                "loanTypeId=" + loanTypeId +
                ", amount=" + amount +
                ", payments=" + payments +
                ", accountToNumber='" + toAccountNumber + '\'' +
                '}';
    }
}
