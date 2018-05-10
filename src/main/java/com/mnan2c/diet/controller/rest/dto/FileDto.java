package com.mnan2c.diet.controller.rest.dto;

import java.time.ZonedDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class FileDto extends AbstractDto {

  private String userId;

  private String url;

  private ZonedDateTime createdDate;

  private String createdDateString;

  private String displayUrl;
}
