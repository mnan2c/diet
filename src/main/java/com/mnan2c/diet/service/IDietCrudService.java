package com.mnan2c.diet.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mnan2c.diet.exceptions.FieldListException;


/**
 * D means Dto
 * @author user
 *
 * @param <D>
 */
public interface IDietCrudService<D> {

	D create(D obj) throws FieldListException;

	D update(D obj) throws FieldListException;

	void deleteById(String id);

	Page<D> getPage(Pageable pageable, String search, String query);

	Page<D> getPage(String search, String query, String expand, Pageable pageable, Boolean withPaginationInfo);

	Page<D> getPage(Pageable pageable);

	D getById(String id);

	D getById(String id, String expand);

	List<D> getAll(List<String> ids);

	List<D> getAll(List<String> ids, String expand);
}
