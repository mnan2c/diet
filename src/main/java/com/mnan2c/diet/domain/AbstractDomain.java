package com.mnan2c.diet.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@SuppressWarnings("serial")
public abstract class AbstractDomain implements Serializable {

	@Id
	private String id;

	@JsonIgnore
	@LastModifiedDate
	@Field("last_modified_date")
	private ZonedDateTime lastModifiedDate = ZonedDateTime.now();

	@JsonIgnore
	@CreatedDate
	@Field("created_date")
	private ZonedDateTime createdDate = ZonedDateTime.now();
}
