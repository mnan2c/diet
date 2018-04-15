package com.mnan2c.diet.controller.rest.dto;

import java.time.ZonedDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class SignInDto extends AbstractDto {

	private String userId;

	private ZonedDateTime signInDate = ZonedDateTime.now();
}
