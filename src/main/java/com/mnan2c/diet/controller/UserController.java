package com.mnan2c.diet.controller;

import java.time.ZonedDateTime;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aliyuncs.exceptions.ClientException;
import com.mnan2c.diet.controller.rest.dto.UserDto;
import com.mnan2c.diet.exceptions.FieldListException;
import com.mnan2c.diet.exceptions.constants.ACommonErrorConstants;
import com.mnan2c.diet.exceptions.constants.ErrorUserConstants;
import com.mnan2c.diet.service.IDietCrudService;
import com.mnan2c.diet.service.SmsService;
import com.mnan2c.diet.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/users")
@Controller
public class UserController extends AbstractController<UserDto> {
  @Inject
  private SmsService smsService;
  @Inject
  private UserService userService;

  @RequestMapping(value = "/sendsms", method = RequestMethod.GET)
  public void sendSms(@RequestParam String cellphoneNumber) throws ClientException {
    smsService.sendSms(cellphoneNumber);
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public ModelAndView createPage() {
    ModelAndView modelAndView = new ModelAndView("register");
    addExtraAttributeForCreatePage(modelAndView);
    return modelAndView;
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ModelAndView register(@ModelAttribute("object") UserDto user, RedirectAttributes redirectAttributes) {
    try {
      validateVerifyCode(user);
      userService.create(user);
    } catch (FieldListException e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
      return redirectTo("/users/register");
    }
    redirectAttributes.addFlashAttribute("message", "Registered successfully.");
    return redirectTo("/login");
  }

  public void validateVerifyCode(UserDto user) throws FieldListException {
    if (session.getAttribute("createdTime") == null //
        || ZonedDateTime.now().isAfter(((ZonedDateTime) session.getAttribute("createdTime")).plusMinutes(30L))) {
      log.error("Verification code is over time");
      throw new FieldListException(ACommonErrorConstants.EntityName.USER, ErrorUserConstants.VERIFY_CODE_TIMEOUT);
    }
    if (!session.getAttribute("verifyCode").equals(user.getVerifyCode())) {
      log.error("verifyCode [{}] dismatch.", user.getVerifyCode(), session.getAttribute("verifyCode"));
      throw new FieldListException(ACommonErrorConstants.EntityName.USER, ErrorUserConstants.INVALID_VERIFY_CODE);
    }
  }

  @Override
  protected String getMainPage() {
    return "";
  }

  @Override
  protected String getControllerUrl() {
    return "/users";
  }

  @Override
  protected List<UserDto> getListObjects() {
    return null;
  }

  @Override
  public IDietCrudService<UserDto> getCrudService() {
    return null;
  }

  @Override
  protected void addExtraAttributeForCreatePage(ModelAndView modelAndView) {
    modelAndView.addObject("object", new UserDto());
  }
}
