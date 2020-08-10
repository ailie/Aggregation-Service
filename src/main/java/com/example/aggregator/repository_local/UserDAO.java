package com.example.aggregator.repository_local;

import org.springframework.data.repository.CrudRepository;

import com.example.aggregator.domain.User;

public interface UserDAO extends CrudRepository<User, String> {

}
