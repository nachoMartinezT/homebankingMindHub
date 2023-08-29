package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients/current/cards")
public class CardController {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam String cardColor, @RequestParam String cardType, Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        CardType type = CardType.valueOf(cardType.toUpperCase());
        CardColor color = CardColor.valueOf(cardType.toUpperCase());
        System.out.println(type);
        if (client.getCards().size() == 3){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Card card = new Card(type, color);
        client.addCard(card);
        cardRepository.save(card);


        return new ResponseEntity<>("client created successfully",HttpStatus.CREATED);
    }
}


