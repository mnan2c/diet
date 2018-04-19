package com.mnan2c.diet.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.mnan2c.diet.domain.SignIn;

public interface SignInRepository extends IDietRepository<SignIn, String> {

  @Query("{'sign_in_date':?0}")
  SignIn findBySignInDate(String signInInDate);

  @Query("{'user_id':?0}")
  List<SignIn> findByUserId(String userId);
}
