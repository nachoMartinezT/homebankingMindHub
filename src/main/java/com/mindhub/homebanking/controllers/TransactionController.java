package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @RequestMapping("/transactions")
    public List<TransactionDto> getTransactions() {
        return transactionService.getTransactions();
    }

    @RequestMapping("/transactions/{id}")
    public TransactionDto findTransactionById(@PathVariable Long id) {
        return transactionService.findTransactionById(id);
    }

    @Transactional
    @RequestMapping(path = "/transactions",method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(Authentication authentication,
            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
            @RequestParam String amount, @RequestParam String description){
       return transactionService.createTransaction(authentication,fromAccountNumber,toAccountNumber,amount,description);
    }


}
