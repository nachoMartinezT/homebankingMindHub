package com.mindhub.homebanking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController()
@RequestMapping("/api")
public class ClientController {

    ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @RequestMapping("/clients")
    public List<ClientDto> getClients(){

        return clientRepository.findAll().stream().map(ClientDto::new).collect(toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDto getClientById(@PathVariable Long id){
        Client foundClient = clientRepository.findById(id).orElse(null);
        ClientDto clientDto = new ClientDto(foundClient);
        return clientDto;
    }


}
