package com.mnan2c.diet.service;

import org.springframework.stereotype.Service;

import com.mnan2c.diet.controller.rest.dto.SignInDto;
import com.mnan2c.diet.domain.SignIn;

@Service
public class SignInService extends AbstractService<SignIn, SignInDto> {

	@Override
	protected String getDtoName() {
		return "signInDto";
	}

}
