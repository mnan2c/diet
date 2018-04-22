package com.mnan2c.diet.controller.rest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto {
  private String name;

  private String openId;

  private String cellphoneNumber;

  private String verifyCode;

  private String password;
}
