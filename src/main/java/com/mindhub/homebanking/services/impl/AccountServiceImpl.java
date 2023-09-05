package com.mindhub.homebanking.services.impl;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDto> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDto::new).collect(toList());
    }

    @Override
    public AccountDto findAccountById(Long id) {
        return new AccountDto(accountRepository.findById(id).orElse(null));
    }

    @Override
    public List<AccountDto> getAccountsOfClient(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
//        return client.getAccounts().stream().map(AccountDto::new).collect(toList());
    }

    @Override
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        return null;
    }
}

//

//    @RequestMapping("/clients/current/accounts")
//    public List<AccountDto> getAccountsOfClient(Authentication authentication){
//
//    }
//
//    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
//    public ResponseEntity<Object> createAccount(Authentication authentication){
//        Client client = clientRepository.findByEmail(authentication.getName());
//
//        if (client == null){
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        if (client.getAccounts().size() <= 3) {
//
//            Account account = new Account(LocalDate.now(), 0);
//            String newAccountNumber = NumberGenerator.accountNumberGenerator();
//            Account accountForNumber = accountRepository.findByNumber(newAccountNumber);
//
//            if (accountForNumber == null){
//                account.setNumber(newAccountNumber);
//            } else {
//                while (accountRepository.findByNumber(newAccountNumber) != null){
//                    newAccountNumber = NumberGenerator.accountNumberGenerator();
//                }
//                account.setNumber(newAccountNumber);
//            }
//
//            client.addAccount(account);
//            clientRepository.save(client);
//            accountRepository.save(account);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>("The customer cannot have more than 3 accounts.",HttpStatus.FORBIDDEN);
//    }
