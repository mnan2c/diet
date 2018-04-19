package com.mnan2c.diet.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.mnan2c.diet.domain.AbstractDomain;

@NoRepositoryBean
public interface IDietRepository<T extends AbstractDomain, I extends Serializable> extends MongoRepository<T, I> {

}
