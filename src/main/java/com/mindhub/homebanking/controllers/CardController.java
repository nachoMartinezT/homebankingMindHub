package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    CardService cardService;


    @RequestMapping("/clients/current/cards")
    public List<CardDto> getCards() {
        return cardService.getCards();
    }

    @PostMapping("/clients/current/cards") //(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam String cardType, @RequestParam String cardColor) {

        return cardService.createCard(authentication, cardType, cardColor);
    }
}


