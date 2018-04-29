package com.mnan2c.diet.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mnan2c.diet.controller.rest.dto.AbstractDto;
import com.mnan2c.diet.domain.AbstractDomain;
import com.mnan2c.diet.exceptions.FieldListException;
import com.mnan2c.diet.exceptions.constants.ACommonErrorConstants;
import com.mnan2c.diet.repository.IDietRepository;
import com.mnan2c.diet.service.converter.AbstractConverter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractService<T extends AbstractDomain, D extends AbstractDto> implements IDietCrudService<D> {

  @Inject
  protected IDietRepository<T, String> repository;

  @Inject
  protected AbstractConverter<T, D> converter;

  @Override
  public D create(D dto) throws FieldListException {
    log.debug("request to create entity [{}]", dto);
    validate(dto, true);
    T entity = converter.dtoToEntity(dto);
    entity = repository.save(entity);
    return converter.entityToDto(entity);
  }

  @Override
  public D update(D dto) throws FieldListException {
    log.debug("request to update entity [{}]", dto);
    T entity = validate(dto, false);
    BeanUtils.copyProperties(dto, entity);
    entity = repository.save(entity);
    return converter.entityToDto(entity);
  }

  @Override
  public void deleteById(String id) {
    repository.delete(id);
  }

  @Override
  public Page<D> findPage(Pageable pageable) {
    Page<T> page = repository.findAll(pageable);
    return converter.getDtoPages(page, pageable);
  }

  @Override
  public Page<D> findPage(Pageable pageable, String search, String query) {
    // TODO
    return null;
  }

  @Override
  public Page<D> findPage(String search, String query, String expand, Pageable pageable, Boolean withPaginationInfo) {
    // TODO
    return null;
  }

  @Override
  public D findById(String id) {
    T entity = repository.findOne(id);
    return converter.entityToDto(entity);
  }

  @Override
  public D findById(String id, String expand) {
    // TODO
    return null;
  }

  @Override
  public List<D> findAll(List<String> ids) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<D> findAll(List<String> ids, String expand) {
    // TODO
    return null;
  }

  protected T validate(D dto, boolean isCreation) throws FieldListException {
    T entityFromDatabase = null;
    if (!isCreation) {
      entityFromDatabase = repository.findOne(dto.getId());
      if (entityFromDatabase == null) {
        log.error("entity not exist by id [{}]", dto.getId());
        throw new FieldListException(getDtoName(), ACommonErrorConstants.ENTITY_NOT_EXISTS);
      }
    }
    businessValidate(dto, isCreation);
    return entityFromDatabase;
  }

  protected void businessValidate(D dto, boolean isCreation) throws FieldListException {
    // validate business
  }

  protected abstract String getDtoName();
}
