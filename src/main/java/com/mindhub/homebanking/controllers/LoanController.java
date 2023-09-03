package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanAplicationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<Object> createLoan(@RequestBody LoanAplicationDto loan){

        if (loan != null) {


        }


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
