package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping("/clients")
    public List<ClientDto> getClients() {
        return clientService.getClients();
    }

    @RequestMapping("/clients/{id}")
    public ClientDto getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password
    ) {

        return clientService.register(firstName, lastName, email, password);
    }

    @GetMapping("/clients/current")
    public ClientDto getClientByEmail(Authentication authentication) {
        return clientService.getClientByEmail(authentication);
    }
}
