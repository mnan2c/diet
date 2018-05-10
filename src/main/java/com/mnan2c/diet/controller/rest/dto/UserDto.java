package com.mnan2c.diet.controller.rest.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto {
  private String name;

  private String openId;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date birthDay;

  private String cellphoneNumber;

  private String verifyCode;

  private String password;
}
