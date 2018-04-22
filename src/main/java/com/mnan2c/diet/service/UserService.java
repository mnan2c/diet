package com.mnan2c.diet.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.mnan2c.diet.controller.rest.dto.UserDto;
import com.mnan2c.diet.domain.User;
import com.mnan2c.diet.repository.UserRepository;
import com.mnan2c.diet.service.converter.UserConverter;

@Service
public class UserService extends AbstractService<User, UserDto> {
  @Inject
  private UserConverter userConverter;
  @Inject
  private UserRepository userRepository;

  @Override
  protected String getDtoName() {
    return "userDto";
  }

  public UserDto findUserByName(String name) {
    return userConverter.entityToDto(userRepository.findByName(name));
  }

  public UserDto findUserByNameAndPassword(String name, String password) {
    return userConverter.entityToDto(userRepository.findByNameAndPassword(name, password));
  }
}
