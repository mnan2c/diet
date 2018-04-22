package com.mnan2c.diet.repository;

import org.springframework.data.mongodb.repository.Query;

import com.mnan2c.diet.domain.User;

public interface UserRepository extends IDietRepository<User, String> {

  @Query("{'name':?0}")
  User findByName(String name);

  @Query("{'$and':[{'name':?0},{'password':?1}]}")
  User findByNameAndPassword(String name, String password);
}
