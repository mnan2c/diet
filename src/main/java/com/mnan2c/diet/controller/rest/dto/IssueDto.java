package com.mnan2c.diet.controller.rest.dto;

import java.time.ZonedDateTime;

import org.springframework.data.annotation.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class IssueDto extends AbstractDto {
  private String description;

  private String userId;

  private ZonedDateTime createdDate;

  @Transient
  private String createDateString;
}
