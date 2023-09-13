package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAccounts();

    AccountDto findAccountById(Long id);

    List<AccountDto> getAccountsOfClient(Authentication authentication);

    void createAccount(Authentication authentication);

}
