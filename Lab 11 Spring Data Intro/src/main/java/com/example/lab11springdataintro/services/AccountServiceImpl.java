package com.example.lab11springdataintro.services;

import com.example.lab11springdataintro.models.Account;
import com.example.lab11springdataintro.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void saveAccount(Account account) {

        accountRepository.save(account);
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {

        Optional<Account> account = accountRepository.findById(id);

        if(account.isPresent() &&
                account.get().getBalance().compareTo(BigDecimal.ZERO) > 0)
        {
            account.get().setBalance(account.get().getBalance().subtract(money));
        }

        accountRepository.save(account.get());
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {

        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent() && money.compareTo(BigDecimal.ZERO) > 0)
        {
            account.get().setBalance(account.get().getBalance().add(money));
        }

        accountRepository.save(account.get());
    }
}
