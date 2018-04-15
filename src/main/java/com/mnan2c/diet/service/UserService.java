package com.mnan2c.diet.service;

import org.springframework.stereotype.Service;

import com.mnan2c.diet.controller.rest.dto.UserDto;
import com.mnan2c.diet.domain.User;

@Service
public class UserService extends AbstractService<User, UserDto>{

	@Override
	protected String getDtoName() {
		return "userDto";
	}
}
