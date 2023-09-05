package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanAplicationDto;
import com.mindhub.homebanking.dtos.LoanDto;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanRepository loanRepository;
    @GetMapping("/loans")
    public List<LoanDto> getLoans(){
       return loanRepository.findAll().stream().map(LoanDto::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan(@RequestBody LoanAplicationDto loan, Authentication authentication){

        if (loan != null) {
            return new ResponseEntity<>("",HttpStatus.FORBIDDEN);
        }

        if (loan.)


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
