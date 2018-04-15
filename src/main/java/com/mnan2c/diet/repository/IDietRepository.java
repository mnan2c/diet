package com.mnan2c.diet.repository;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mnan2c.diet.domain.AbstractDomain;

@NoRepositoryBean
public interface IDietRepository<T extends AbstractDomain, I extends Serializable> extends PagingAndSortingRepository<T, I>{

}
