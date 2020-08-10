package com.example.aggregator.repository_local;

import org.springframework.data.repository.CrudRepository;

import com.example.aggregator.domain.Transaction;

public interface TransactionDAO extends CrudRepository<Transaction, String> {

    Iterable<Transaction> findByAccountId(String accountId);
}
