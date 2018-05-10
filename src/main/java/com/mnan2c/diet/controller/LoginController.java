package com.mnan2c.diet.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mnan2c.diet.constants.DietConstants;
import com.mnan2c.diet.controller.rest.dto.UserDto;
import com.mnan2c.diet.service.IDietCrudService;
import com.mnan2c.diet.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController extends AbstractController<UserDto> {
  @Inject
  private UserService userService;

  @RequestMapping(value = {"/login", "/", ""}, method = RequestMethod.GET)
  public ModelAndView loginPage() {
    if (session.getAttribute(DietConstants.SESSION_USER) != null) {
      return indexPage();
    }
    ModelAndView modelAndView = new ModelAndView("login");
    modelAndView.addObject("object", new UserDto());
    return modelAndView;
  }

  @PostMapping("/loginPost")
  public ModelAndView loginPost(@ModelAttribute("object") UserDto user, BindingResult result) {
    if (userService.findUserByName(user.getName()) == null) {
      log.error("user not exits, name=[{}]", user.getName());
      result.rejectValue("name", null, null, "user.not.exist");
      return new ModelAndView("login").addAllObjects(result.getModel());
    }
    UserDto dto = userService.findUserByNameAndPassword(user.getName(), user.getPassword());
    if (dto == null) {
      log.error("invalid password, name=[{}],password=[{}]", user.getName(), user.getPassword());
      result.rejectValue("password", null, null, "invalid.password");
      return new ModelAndView("login").addAllObjects(result.getModel());
    }
    // 设置session
    session.setAttribute(DietConstants.SESSION_USER, dto);
    session.setMaxInactiveInterval(60 * 30);
    return indexPage();
  }

  @GetMapping("/index")
  public ModelAndView indexPage() {
    ModelAndView modelAndView = new ModelAndView("index");
    modelAndView.addObject("user", session.getAttribute(DietConstants.SESSION_USER));
    return modelAndView;
  }

  @Override
  protected String getMainPage() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected String getControllerUrl() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<UserDto> getListObjects() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IDietCrudService<UserDto> getCrudService() {
    // TODO Auto-generated method stub
    return null;
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
