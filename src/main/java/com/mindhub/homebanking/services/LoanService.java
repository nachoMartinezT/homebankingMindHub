package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanAplicationDto;
import com.mindhub.homebanking.dtos.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LoanService {


    List<LoanDto> getLoans();

    ResponseEntity<Object> createLoan(LoanAplicationDto loanAplicationDto, Authentication authentication);


}
