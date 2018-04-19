package com.mnan2c.diet.repository;

import org.springframework.data.mongodb.repository.Query;

import com.mnan2c.diet.domain.User;

public interface UserRepository extends IDietRepository<User, String> {

  @Query("{'name':?0}")
  User findByName(String name);
}
