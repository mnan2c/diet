package com.mnan2c.diet.service.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.mnan2c.diet.controller.rest.dto.SignInDto;
import com.mnan2c.diet.domain.SignIn;

@Component
public class SignInConverter extends AbstractConverter<SignIn, SignInDto> {

  @Override
  public SignIn dtoToEntity(SignInDto dto) {
    if (dto == null) {
      return null;
    }
    SignIn entity = new SignIn();
    BeanUtils.copyProperties(dto, entity);
    return entity;
  }

  @Override
  public SignInDto entityToDto(SignIn entity) {
    if (entity == null) {
      return null;
    }
    SignInDto dto = new SignInDto();
    copyEntityToDto(entity, dto);
    return dto;
  }

}
