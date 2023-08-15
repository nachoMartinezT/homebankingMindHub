package com.mindhub.homebanking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<AccountDto> accounts = new HashSet<>();

    private Set<ClientLoanDto> clientLoans = new HashSet<>();

    public ClientDto() {

    }

    public ClientDto(Client client) {

        this.id = client.getId();

        this.firstName = client.getFirstName();

        this.lastName = client.getLastName();

        this.email = client.getEmail();

        this.accounts = convertSetToDtoSet(client.getAccounts());

        this.clientLoans = client.getClientLoans().stream()
                .map(ClientLoanDto::new)
                .collect(Collectors.toSet());
    }


    public Long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }

    public Set<AccountDto> getAccounts(){
        return accounts;
    }

    public Set<ClientLoanDto> getClientLoans() {
        return clientLoans;
    }

    public Set<AccountDto> convertSetToDtoSet(Set<Account> accounts){
        return accounts.stream()
                .map(AccountDto::new)
                        .collect(Collectors.toSet());
    }

}

//account.getId(),account.getNumber(),account.getCreationDate(),account.getBalance()