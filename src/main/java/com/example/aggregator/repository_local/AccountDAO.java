package com.example.aggregator.repository_local;

import org.springframework.data.repository.CrudRepository;

import com.example.aggregator.domain.Account;

public interface AccountDAO extends CrudRepository<Account, String> {

    Iterable<Account> findByUserId(String userId);
}
