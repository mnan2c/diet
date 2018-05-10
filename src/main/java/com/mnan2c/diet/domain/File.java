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
@Document(collection = "file")
public class File extends AbstractDomain {

  @Field("user_id")
  private String userId;

  @Field("url")
  private String url;
}
