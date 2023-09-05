package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanAplicationDto;
import com.mindhub.homebanking.dtos.LoanDto;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/loans")
    public List<LoanDto> getLoans(){
       return loanRepository.findAll().stream().map(LoanDto::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan(@RequestBody LoanAplicationDto loanAplicationDto, Authentication authentication){


        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null){
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }

        if (accountRepository.findByNumber(loanAplicationDto.getAccount()) == null){
            return new ResponseEntity<>("Account not found", HttpStatus.FORBIDDEN);
        }

        if (!client.getAccounts().contains(accountRepository.findByNumber(loanAplicationDto.getAccount()))){
            return new ResponseEntity<>("The account does not belong to the client", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanRepository.findById(loanAplicationDto.getId()).orElse(null);

        if (loan == null || loanAplicationDto.getAmount() > loan.getMaxAmount()  || !loan.getPayments().contains(loanAplicationDto.getPayments())){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        loan.setMaxAmount(loanAplicationDto.getAmount() * 1.20);


        Transaction transaction = new Transaction(TransactionType.CREDIT,loan.getMaxAmount(),"Loan", LocalDateTime.now());
        Account account = accountRepository.findByNumber(loanAplicationDto.getAccount());
        transaction.setAccount(account);
        account.setBalance(account.getBalance() + loan.getMaxAmount());
        transactionRepository.save(transaction);
        accountRepository.save(account);
        loanRepository.save(loan);

        return new ResponseEntity<>("Loan created successfully",HttpStatus.CREATED);
    }
}
