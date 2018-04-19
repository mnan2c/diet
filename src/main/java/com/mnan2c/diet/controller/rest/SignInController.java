package com.mnan2c.diet.controller.rest;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mnan2c.diet.controller.rest.dto.SignInDto;
import com.mnan2c.diet.domain.SignIn;
import com.mnan2c.diet.exceptions.FieldListException;
import com.mnan2c.diet.service.SignInService;

@RequestMapping("/api/signin")
@RestController
public class SignInController extends ACrudController<SignIn, SignInDto> {
  @Inject
  private SignInService signInService;

  @Override
  protected String getAlertEntityName() {
    return "SignIn";
  }

  @Override
  protected String getApiRootPath() {
    return "/api/signin/";
  }

  @RequestMapping(value = "/ontheway", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> signIn(@RequestParam String name) throws FieldListException {
    return new ResponseEntity<>(signInService.signInOnTheWay(name), HttpStatus.OK);
  }
}
