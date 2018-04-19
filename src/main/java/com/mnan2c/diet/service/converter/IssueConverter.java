package com.mnan2c.diet.service.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.mnan2c.diet.controller.rest.dto.IssueDto;
import com.mnan2c.diet.domain.Issue;

@Component
public class IssueConverter extends AbstractConverter<Issue, IssueDto> {

  @Override
  public Issue dtoToEntity(IssueDto dto) {
    Issue entity = new Issue();
    BeanUtils.copyProperties(dto, entity);
    return entity;
  }

  @Override
  public IssueDto entityToDto(Issue entity) {
    IssueDto dto = new IssueDto();
    copyEntityToDto(entity, dto);
    return dto;
  }

  @Override
  protected void copyEntityToDto(Issue entity, IssueDto dto) {
    BeanUtils.copyProperties(entity, dto, "lastModifiedDate");
  }
}
