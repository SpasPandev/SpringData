package com.example.lab11springdataintro;

import com.example.lab11springdataintro.models.Account;
import com.example.lab11springdataintro.models.User;
import com.example.lab11springdataintro.services.AccountService;
import com.example.lab11springdataintro.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final AccountService accountService;

    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        User pesho = new User();
        pesho.setUsername("pesho");
        pesho.setAge(23);

        List<Account> accountsLists = new ArrayList<>();

        Account peshosAccount = new Account();
        peshosAccount.setBalance(BigDecimal.TEN);
        peshosAccount.setUser(pesho);
        accountsLists.add(peshosAccount);

        pesho.setAccounts(accountsLists);


        userService.registerUser(pesho);
        accountService.saveAccount(peshosAccount);

        accountService.withdrawMoney(BigDecimal.ONE, pesho.getId());

        accountService.transferMoney(new BigDecimal(1000), pesho.getId());

    }
}
