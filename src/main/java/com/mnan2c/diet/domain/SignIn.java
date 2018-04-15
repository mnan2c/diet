package com.mnan2c.diet.domain;

import java.time.ZonedDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Document(collection = "sign_in")
public class SignIn extends AbstractDomain {

	@Field("user_id")
	private String userId;

	@Field("sign_in_date")
	private ZonedDateTime signInDate;
}
