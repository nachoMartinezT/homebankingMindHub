package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;


    @RequestMapping("/transactions")
    public List<TransactionDto> getTransactions() {
        return transactionService.getTransactions();
    }

    @RequestMapping("/transactions/{id}")
    public TransactionDto findTransactionById(@PathVariable Long id) {
        return transactionService.findTransactionById(id);
    }

    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                    @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
                                                    @RequestParam Double amount, @RequestParam String description) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null)
            return new ResponseEntity<>("Only a logged-in customer can generate a transaction", HttpStatus.FORBIDDEN);

        if (amount.isNaN() || amount <= 0)
            return new ResponseEntity<>("The amount must be numeric and greater than zero.", HttpStatus.FORBIDDEN);

        if (description.isBlank())
            return new ResponseEntity<>("The description cannot be empty", HttpStatus.FORBIDDEN);

        if (fromAccountNumber.isBlank())
            return new ResponseEntity<>("The source account cannot be empty", HttpStatus.FORBIDDEN);

        if (toAccountNumber.isBlank())
            return new ResponseEntity<>("The destination account cannot be empty", HttpStatus.FORBIDDEN);

        if (accountRepository.findByNumber(fromAccountNumber).getNumber() == null)
            return new ResponseEntity<>("The source account does not exist", HttpStatus.FORBIDDEN);

        if (accountRepository.findByNumber(toAccountNumber).getNumber() == null)
            return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);

        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("Accounts cannot be the same", HttpStatus.FORBIDDEN);
        }

        if (accountRepository.findByNumber(fromAccountNumber).getBalance() < amount) {
            return new ResponseEntity<>("Not sufficient founds", HttpStatus.FORBIDDEN);
        }


        transactionService.createTransaction(authentication, fromAccountNumber, toAccountNumber, amount, description);
        return new ResponseEntity<>("The transaction was successful", HttpStatus.CREATED);
    }


}
