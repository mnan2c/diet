package com.mnan2c.diet.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.mnan2c.diet.controller.rest.dto.SignInDto;
import com.mnan2c.diet.domain.SignIn;
import com.mnan2c.diet.domain.User;
import com.mnan2c.diet.exceptions.FieldListException;
import com.mnan2c.diet.exceptions.ResponseCode;
import com.mnan2c.diet.repository.SignInRepository;
import com.mnan2c.diet.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SignInService extends AbstractService<SignIn, SignInDto> {
  @Inject
  private SignInRepository signInRepository;
  @Inject
  private UserRepository userRepository;

  @Override
  protected String getDtoName() {
    return "signInDto";
  }

  public Map<String, Object> signInOnTheWay(String name) throws FieldListException {
    Map<String, Object> result = new HashMap<>();
    // check if user exists
    User user = userRepository.findByName(name);
    if (user == null) {
      log.error("user not exist, name=[{}]", name);
      result.put("code", ResponseCode.NOT_EXIST);
    }
    // check if has already signed in
    SignIn isSignIn = signInRepository
        .findBySignInDate(ZonedDateTime.now(ZoneId.of("UTC+08:00")).format(DateTimeFormatter.BASIC_ISO_DATE));
    if (isSignIn != null) {
      log.error("user already signed in, id=[{}]", user.getId());
      result.put("code", ResponseCode.DUPLICATED);
    }
    // add sign in record
    SignInDto signInDto = new SignInDto();
    signInDto.setSignInDate(ZonedDateTime.now(ZoneId.of("UTC+08:00")).format(DateTimeFormatter.BASIC_ISO_DATE));
    signInDto.setUserId(user.getId());
    create(signInDto);
    // find all sign in record
    List<SignIn> signIns = signInRepository.findByUserId(user.getId());
    result.put("total", signIns.size());
    return result;
  }

}
