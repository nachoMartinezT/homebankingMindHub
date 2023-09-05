package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

public interface AccountService {

    List<AccountDto> getAccounts();
    AccountDto findAccountById(Long id);
    List<AccountDto> getAccountsOfClient(Authentication authentication);
    ResponseEntity<Object> createAccount(Authentication authentication);

}
