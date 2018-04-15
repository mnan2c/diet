package com.mnan2c.diet.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnan2c.diet.controller.rest.dto.SignInDto;
import com.mnan2c.diet.domain.SignIn;

@RequestMapping("/api/signin")
@RestController
public class SignInController extends ACrudController<SignIn, SignInDto> {

	@Override
	protected String getAlertEntityName() {
		return "SignIn";
	}

	@Override
	protected String getApiRootPath() {
		return "/api/signin/";
	}

}
