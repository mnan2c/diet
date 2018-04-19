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
@Document(collection = "issue")
public class Issue extends AbstractDomain {

  @Field("description")
  private String description;

  @Field("user_id")
  private String userId;
}
