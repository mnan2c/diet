package com.mnan2c.diet.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.mnan2c.diet.controller.rest.dto.IssueDto;
import com.mnan2c.diet.domain.Issue;
import com.mnan2c.diet.repository.IssueRepository;
import com.mnan2c.diet.service.converter.IssueConverter;

@Service
public class IssueService extends AbstractService<Issue, IssueDto> {
  @Inject
  private IssueRepository issueRepository;
  @Inject
  private IssueConverter issueConverter;

  @Override
  protected String getDtoName() {
    return "issueDto";
  }

  public List<IssueDto> getAllIssueDescriptions() {
    return issueConverter.entitiesToDtos(issueRepository.findByDescription());
  }

}
