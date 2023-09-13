package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionService {

    List<TransactionDto> getTransactions();

    TransactionDto findTransactionById(@PathVariable Long id);

    void createTransaction(Authentication authentication, String fromAccountNumber, String toAccountNumber, Double amount, String description);


}
