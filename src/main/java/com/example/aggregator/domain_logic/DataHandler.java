package com.example.aggregator.domain_logic;

import static org.slf4j.LoggerFactory.getLogger;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.aggregator.domain.Account;
import com.example.aggregator.domain.Transaction;
import com.example.aggregator.domain.User;
import com.example.aggregator.repository_local.AccountDAO;
import com.example.aggregator.repository_local.TransactionDAO;
import com.example.aggregator.repository_local.UserDAO;
import com.example.aggregator.repository_remote.RemoteRepository;

@Component
@EnableScheduling
public class DataHandler {

    private static final Logger LOG = getLogger(DataHandler.class);

    private final UserDAO          userDAO;
    private final AccountDAO       accountDAO;
    private final TransactionDAO   transactionDAO;
    private final RemoteRepository remoteRepository;

    DataHandler(UserDAO userDAO, AccountDAO accountDAO, TransactionDAO transactionDAO,
                RemoteRepository remoteRepository) {
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
        this.remoteRepository = remoteRepository;
    }

    public Iterable<User> listUsers() {
        return userDAO.findAll();
    }

    public Iterable<Account> listAccountsByUser(String userId) {
        return accountDAO.findByUserId(userId);
    }

    public Iterable<Transaction> listTransactionByAccount(String accountId) {
        return transactionDAO.findByAccountId(accountId);
    }

    @PostConstruct
    void onStart() {

        if (userDAO.count() < 1) {
            // Add some imaginary users to our DB, for testing purposes...
            userDAO.save(new User("niliescu", "Nicolae Iliescu"));
            userDAO.save(new User("eiohanis", "Emil Iohanis"));
        }

        updateLocalStorage();
    }

    /** For each of our own users, pull data from remote service and store it locally. */
    @Scheduled(/* daily, at 62s past UTC midnight */ cron = "2 1 0 * * *", zone = "UTC")
    void updateLocalStorage() {
        for (User user : userDAO.findAll()) {
            remoteRepository.findAllAccounts(user.getId()).forEach(accountDAO::save);
            remoteRepository.findAllTransactions(user.getId()).forEach(transactionDAO::save);
        }
    }

    @SuppressWarnings("unused")
    private void printToConsoleTheWholeDB() /* for smoke-testing during dev... */ {
        for (User user : userDAO.findAll()) {
            LOG.info(">> Accounts for {} <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", user.getId());
            for (Account account : accountDAO.findByUserId(user.getId())) {
                LOG.info(">>>> {}", account);
                for (Transaction transaction : transactionDAO.findByAccountId(account.getId())) {
                    LOG.info(">>>>>> {}", transaction);
                }
            }
        }
    }
}
