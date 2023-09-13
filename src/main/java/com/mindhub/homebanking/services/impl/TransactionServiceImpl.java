package com.mindhub.homebanking.services.impl;

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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;


    public List<TransactionDto> getTransactions() {
        return transactionRepository.findAll().stream().map(TransactionDto::new).collect(toList());
    }


    public TransactionDto findTransactionById(@PathVariable Long id) {
        return new TransactionDto(transactionRepository.findById(id).orElse(null));
    }


    public void createTransaction(Authentication authentication, String fromAccountNumber, String toAccountNumber, Double amount, String description) {

        Client client = clientRepository.findByEmail(authentication.getName());

        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, amount, description + " " + fromAccountNumber, LocalDateTime.now());
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, description + " " + toAccountNumber, LocalDateTime.now());

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        accountRepository.save(processTransfer(fromAccountNumber, debitTransaction));
        accountRepository.save(processTransfer(toAccountNumber, creditTransaction));


    }

    private Account processTransfer(String accountNumberForTransaction, Transaction transaction) {
        Account account = accountRepository.findByNumber(accountNumberForTransaction);
        account.addTransaction(transaction);
        if (transaction.getType().equals(TransactionType.CREDIT)) {
            account.setBalance(account.getBalance() + transaction.getAmount());
        } else {
            account.setBalance(account.getBalance() - transaction.getAmount());
        }
        return account;
    }
}
