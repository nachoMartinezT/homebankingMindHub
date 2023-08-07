package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {

    AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository){this.accountRepository = accountRepository;}

    @RequestMapping("/accounts")
        public List<AccountDto> getAccounts(){

        return accountRepository.findAll().stream().map(AccountDto::new).collect(toList());

    }

}


