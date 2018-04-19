package com.mnan2c.diet.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.mnan2c.diet.domain.Issue;

public interface IssueRepository extends IDietRepository<Issue, String> {

  @Query("{'description':{'$exists':true}}")
  List<Issue> findByDescription();
}
