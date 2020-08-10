package com.example.aggregator.presentation;

import static org.springframework.util.StringUtils.isEmpty;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aggregator.domain.Account;
import com.example.aggregator.domain.Transaction;
import com.example.aggregator.domain.User;
import com.example.aggregator.domain_logic.DataHandler;

@RestController
public class FrontController {

    private final DataHandler dataHandler;

    private FrontController(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    @GetMapping("/users")
    private Iterable<User> listUsers() {
        return dataHandler.listUsers();
    }

    @GetMapping("/accounts")
    private Iterable<Account> listAccountsByUser(
            @RequestParam(value = "user", required = false) String user) {
        if (isEmpty(user)) {
            throw new UnsupportedOperationException("Please specify an username, eg: /accounts?user=FOO");
        } else {
            return dataHandler.listAccountsByUser(user);
        }
    }

    @GetMapping("/transactions")
    private Iterable<Transaction> listTransactionByAccount(
        @RequestParam(value = "account", required = false) String account) {
        if (isEmpty(account)) {
            throw new UnsupportedOperationException("Please specify an account, eg: /transactions?account=BAR");
        } else {
            return dataHandler.listTransactionByAccount(account);
        }
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    private String showError(Throwable throwable) {
        return throwable.getMessage();
    }
}
