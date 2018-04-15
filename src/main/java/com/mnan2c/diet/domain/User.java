package com.mnan2c.diet.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Document(collection = "user")
public class User extends AbstractDomain {

	@Field("name")
	private String name;

	@Field("open_id")
	private String openId;

	@Field("cellphone_number")
	private String cellphoneNumber;
}
