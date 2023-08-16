package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDto;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;


    @RequestMapping("/transactions")
    public List<TransactionDto> getAccounts() {
        return transactionRepository.findAll().stream().map(TransactionDto::new).collect(toList());
    }

    @RequestMapping("/transactions/{id}")
    public TransactionDto findTransactionById(@PathVariable Long id) {
        return new TransactionDto(transactionRepository.findById(id).orElse(null));
    }
}
