package com.mnan2c.diet.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mnan2c.diet.controller.rest.dto.IssueDto;
import com.mnan2c.diet.service.IDietCrudService;
import com.mnan2c.diet.service.IssueService;

@RequestMapping("/issues")
@Controller
public class IssueController extends AbstractController<IssueDto> {
  @Inject
  private IssueService issueService;

  @Override
  protected String getMainPage() {
    return "issue";
  }

  @Override
  public IDietCrudService<IssueDto> getCrudService() {
    return issueService;
  }

  @Override
  protected List<IssueDto> getListObjects() {
    List<IssueDto> list = issueService.getAllIssueDescriptions() //
        .stream() //
        .map(issue -> {
          issue.setCreateDateString(issue.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
          return issue;
        }) //
        .collect(Collectors.toList());
    list.sort((is1, is2) -> is2.getCreatedDate().compareTo(is1.getCreatedDate()));
    return list;
  }

  @Override
  protected String getControllerUrl() {
    return "/issues";
  }
}
