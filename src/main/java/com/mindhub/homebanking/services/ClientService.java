package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.utils.NumberGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

public interface ClientService {
    List<ClientDto> getClients();

    ClientDto getClientById(Long id);

    ResponseEntity<Object> register(String firstName, String lastName, String email, String password);

    public ClientDto getClientByEmail(Authentication authentication);

}
