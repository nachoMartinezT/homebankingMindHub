package com.mindhub.homebanking.services.impl;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<ClientDto> getClients() {
        return clientRepository.findAll().stream().map(ClientDto::new).collect(toList());
    }

    public ClientDto getClientById(Long id) {
        Client foundClient = clientRepository.findById(id).orElse(null);
        ClientDto clientDto = new ClientDto(foundClient);
        return clientDto;
    }


    public ResponseEntity<Object> register(String firstName, String lastName, String email, String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        Account account = new Account(LocalDate.now(), 0);
        account.setNumber(NumberGenerator.accountNumberGenerator());
        client.addAccount(account);
        clientRepository.save(client);
        accountRepository.save(account);


        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public ClientDto getClientByEmail(Authentication authentication) {
        Client authClient = clientRepository.findByEmail(authentication.getName());
        ClientDto clientDto = new ClientDto(authClient);
        return clientDto;
    }

}
