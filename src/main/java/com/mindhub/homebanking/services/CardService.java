package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardService {
    List<CardDto> getCards();


    ResponseEntity<Object> createCard(Authentication authentication, String cardType, String cardColor);


}
