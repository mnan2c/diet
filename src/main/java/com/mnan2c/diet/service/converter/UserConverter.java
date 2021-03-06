package com.mnan2c.diet.service.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.mnan2c.diet.controller.rest.dto.UserDto;
import com.mnan2c.diet.domain.User;

@Component
public class UserConverter extends AbstractConverter<User, UserDto> {

  @Override
  public User dtoToEntity(UserDto dto) {
    if (dto == null) {
      return null;
    }
    User entity = new User();
    BeanUtils.copyProperties(dto, entity);
    return entity;
  }

  @Override
  public UserDto entityToDto(User entity) {
    if (entity == null) {
      return null;
    }
    UserDto dto = new UserDto();
    copyEntityToDto(entity, dto);
    return dto;
  }
}
