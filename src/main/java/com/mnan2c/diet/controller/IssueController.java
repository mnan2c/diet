package com.mnan2c.diet.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mnan2c.diet.controller.rest.dto.IssueDto;
import com.mnan2c.diet.controller.rest.dto.UserDto;
import com.mnan2c.diet.service.IDietCrudService;
import com.mnan2c.diet.service.IssueService;
import com.mnan2c.diet.service.UserService;

@RequestMapping("/issues")
@Controller
public class IssueController extends AbstractController<IssueDto> {
  @Inject
  private IssueService issueService;
  @Inject
  private UserService userService;

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
          if (StringUtils.isNotBlank(issue.getUserId())) {
            UserDto dto = userService.findById(issue.getUserId());
            issue.setUser(dto);
          }
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

  @Override
  protected void addExtraAttributeForCreatePage(ModelAndView modelAndView) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void addExtraAttributeForEditPage(ModelAndView modelAndView) {
    // TODO Auto-generated method stub

  }
}
