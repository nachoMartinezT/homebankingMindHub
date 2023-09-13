package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Role;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDto> getAccounts() {
        return accountService.getAccounts();
    }


    @RequestMapping("/accounts/{id}")
    public AccountDto findAccountById(@PathVariable Long id) {
        return accountService.findAccountById(id);
    }

    @RequestMapping("/clients/current/accounts")
    public List<AccountDto> getAccountsOfClient(Authentication authentication) {
        return accountService.getAccountsOfClient(authentication);
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null)
            return new ResponseEntity<>("Only a logged-in customer can create an account", HttpStatus.FORBIDDEN);

        if (client.getRole() != Role.CLIENT)
            return new ResponseEntity<>("The customer is not authorized to create an account.", HttpStatus.FORBIDDEN);

        if (client.getAccounts().size() >= 3)
            return new ResponseEntity<>("Clients cannot have more than 3 accounts", HttpStatus.FORBIDDEN);

        accountService.createAccount(authentication);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}


