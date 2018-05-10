package com.mnan2c.diet.controller.rest;

import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnan2c.diet.controller.rest.dto.UserDto;
import com.mnan2c.diet.domain.User;
import com.mnan2c.diet.exceptions.FieldListException;

@RequestMapping("/api/users")
@RestController
public class UserRestController extends ACrudController<User, UserDto> {

  @Override
  protected String getAlertEntityName() {
    return "User";
  }

  @Override
  protected String getApiRootPath() {
    return "/api/users/";
  }

  @Override
  public ResponseEntity<UserDto> create(UserDto user) throws FieldListException, URISyntaxException {
    return super.create(user);
  }

}
