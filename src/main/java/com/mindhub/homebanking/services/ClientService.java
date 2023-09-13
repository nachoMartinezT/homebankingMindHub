package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    List<ClientDto> getClients();

    ClientDto getClientById(Long id);

    ResponseEntity<Object> register(String firstName, String lastName, String email, String password);

    public ClientDto getClientByEmail(Authentication authentication);

}
