package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDto> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDto::new).collect(toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDto findAccountById(@PathVariable Long id) {
        return new AccountDto(accountRepository.findById(id).orElse(null));
    }

    @RequestMapping("/clients/current/accounts")
    public List<AccountDto> getAccountsOfClient(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDto::new).collect(toList());
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getAccounts().size() < 3){
            Account account = new Account(LocalDate.now(), 0);
            String newAccountNumber = "";
            do{
                newAccountNumber = NumberGenerator.accountNumberGenerator();
            } while(newAccountNumber.equals(accountRepository.findByNumber(newAccountNumber).getNumber()));
            account.setNumber(newAccountNumber);
            System.out.println(account.getNumber());
            client.addAccount(account);
            clientRepository.save(client);
            accountRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>("The customer cannot have more than 3 accounts.",HttpStatus.FORBIDDEN);
    }

}


