package com.mnan2c.diet.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnan2c.diet.controller.rest.dto.UserDto;
import com.mnan2c.diet.domain.User;

@RequestMapping("/api/users")
@RestController
public class UserRestController extends ACrudController<User, UserDto>{

	@Override
	protected String getAlertEntityName() {
		return "User";
	}

	@Override
	protected String getApiRootPath() {
		return "/api/users/";
	}
}
