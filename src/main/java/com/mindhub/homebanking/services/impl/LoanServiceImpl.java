package com.mindhub.homebanking.services.impl;

import com.mindhub.homebanking.dtos.LoanAplicationDto;
import com.mindhub.homebanking.dtos.LoanDto;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;


    public List<LoanDto> getLoans(){
        return loanRepository.findAll().stream().map(LoanDto::new).collect(Collectors.toList());
    }

    public ResponseEntity<Object> createLoan(LoanAplicationDto loanAplicationDto, Authentication authentication){


        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null){
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }

        Account account = accountRepository.findByNumber(loanAplicationDto.getToAccountNumber());

        if (account == null){
            return new ResponseEntity<>("Account not found", HttpStatus.FORBIDDEN);
        }

        if (!client.getAccounts().contains(accountRepository.findByNumber(loanAplicationDto.getToAccountNumber()))){
            return new ResponseEntity<>("The account does not belong to the client", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanRepository.findById(loanAplicationDto.getLoanTypeId()).orElse(null);

        if (loan == null || loanAplicationDto.getAmount() > loan.getMaxAmount()  || !loan.getPayments().contains(loanAplicationDto.getPayments())){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan();

        clientLoan.setLoan(loan);
        clientLoan.getPayments().add(loanAplicationDto.getPayments());
        clientLoanRepository.save(clientLoan);
        client.addClientLoan(clientLoan);
        clientRepository.save(client);



        Transaction transaction = new Transaction(TransactionType.CREDIT,loan.getMaxAmount(),"Loan", LocalDateTime.now());

        transaction.setAccount(account);
        account.setBalance(account.getBalance() + loanAplicationDto.getAmount());
        transactionRepository.save(transaction);
        accountRepository.save(account);
        loanRepository.save(loan);

        return new ResponseEntity<>("Loan created successfully",HttpStatus.CREATED);
    }
}
