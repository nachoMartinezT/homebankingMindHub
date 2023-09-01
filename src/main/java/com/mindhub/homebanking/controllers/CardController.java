package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;

    @RequestMapping("/clients/current/cards")
    public List<CardDto> getCards(){
        return cardRepository.findAll().stream().map(CardDto::new).collect(Collectors.toList());
    }

    @PostMapping("/clients/current/cards") //(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication,@RequestParam String cardType, @RequestParam String cardColor) {
        Client client = clientRepository.findByEmail(authentication.getName());
        CardType type = CardType.valueOf(cardType.toUpperCase());
        CardColor color = CardColor.valueOf(cardColor.toUpperCase());

        if (client.getCards().size() <= 3){
            return new ResponseEntity<>("The user cannot have more than three cards",HttpStatus.FORBIDDEN);
        }

        Card card = new Card(type, color);

        String newCardNumber = "";
        Card cardForNumber =  cardRepository.findByNumber(newCardNumber);
        newCardNumber = NumberGenerator.cardNumberGenerator();
        if (cardForNumber != null){
            while(newCardNumber.equals(cardForNumber.getNumber())) {
                    newCardNumber = NumberGenerator.cardNumberGenerator();
            }
            card.setNumber(newCardNumber);
        } else {
            card.setNumber(newCardNumber);
        }

        card.setCardHolder(client.getFirstName() + " " + client.getLastName());
        card.setNumber(newCardNumber);
        card.setCvv(NumberGenerator.cvvNumberGenerator());
        card.setFromDate(LocalDate.now());
        card.setThruDate(LocalDate.now().plusYears(5));
        client.addCard(card);
        cardRepository.save(card);

        return new ResponseEntity<>("client created successfully",HttpStatus.CREATED);
    }
}


