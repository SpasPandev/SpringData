package com.example.lab11springdataintro.services;

import com.example.lab11springdataintro.models.Account;

import java.math.BigDecimal;

public interface AccountService {

    void saveAccount(Account account);

    void withdrawMoney(BigDecimal money, Long id);

    void transferMoney(BigDecimal money, Long id);
}
