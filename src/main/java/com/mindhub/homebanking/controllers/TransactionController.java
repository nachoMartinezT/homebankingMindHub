package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
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
    TransactionRepository transactionRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;


    @RequestMapping("/transactions")
    public List<TransactionDto> getTransactions() {
        return transactionRepository.findAll().stream().map(TransactionDto::new).collect(toList());
    }

    @RequestMapping("/transactions/{id}")
    public TransactionDto findTransactionById(@PathVariable Long id) {
        return new TransactionDto(transactionRepository.findById(id).orElse(null));
    }


    /*@Transactional*/
    @RequestMapping(path = "/transactions",method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(Authentication authentication,
            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
            @RequestParam String amount, @RequestParam String description){

        Client client = clientRepository.findByEmail(authentication.getName());

        if (( amount.isBlank()) || description.isBlank() || fromAccountNumber.isBlank() || toAccountNumber.isBlank()){
            return new ResponseEntity<>("The fields cannot be empty",HttpStatus.FORBIDDEN);
        }

        Double amountNumber = Double.parseDouble(amount);

        if (accountRepository.findByNumber(fromAccountNumber).getNumber() == null || accountRepository.findByNumber(toAccountNumber).getNumber() == null){
            return new ResponseEntity<>("One of the accounts you are trying to use does not exist",HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("Accounts cannot be the same",HttpStatus.FORBIDDEN);
        }

        boolean hasSufficientFounds = client.getAccounts().stream().filter(account -> account.getNumber().equals(fromAccountNumber)).anyMatch(account -> account.getBalance() >= amountNumber);
        if (!hasSufficientFounds){
            return new ResponseEntity<>("Not sufficient founds",HttpStatus.FORBIDDEN);
        }


        Transaction debitTransaction = new Transaction(TransactionType.DEBIT,amountNumber,description + " " + fromAccountNumber, LocalDateTime.now());
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT,amountNumber, description + " " + toAccountNumber, LocalDateTime.now());


        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        accountRepository.save(processTransfer(fromAccountNumber,debitTransaction));
        accountRepository.save(processTransfer(toAccountNumber,creditTransaction));

        return new ResponseEntity<>("The transfer was successful",HttpStatus.CREATED);

    }

    private Account processTransfer(String accountNumberForTransaction, Transaction transaction){
        Account account = accountRepository.findByNumber(accountNumberForTransaction);
        account.addTransaction(transaction);
        if (transaction.getType().equals(TransactionType.CREDIT)){
            account.setBalance(account.getBalance() + transaction.getAmount());
        } else {
            account.setBalance(account.getBalance() - transaction.getAmount());
        }
        return account;
    }



}
