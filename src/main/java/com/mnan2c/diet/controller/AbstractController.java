package com.mnan2c.diet.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mnan2c.diet.constants.DietConstants;
import com.mnan2c.diet.controller.rest.dto.AbstractDto;
import com.mnan2c.diet.controller.rest.dto.UserDto;
import com.mnan2c.diet.exceptions.FieldListException;
import com.mnan2c.diet.service.IDietCrudService;


public abstract class AbstractController<T extends AbstractDto> {
  @Inject
  protected HttpSession session;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView listPage() {
    ModelAndView modelAndView = new ModelAndView(getMainPage() + "/list");
    List<T> list = getListObjects();
    modelAndView.addObject("list", list);
    // modelAndView.addObject("object", attributeValue);
    return modelAndView;
  }

  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView createPage() {
    ModelAndView modelAndView = new ModelAndView(getMainPage() + "/form");
    addExtraAttributeForCreatePage(modelAndView);
    return modelAndView;
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public ModelAndView editPage(@ModelAttribute("object") T t) {
    ModelAndView modelAndView = new ModelAndView(getMainPage() + "/form");
    modelAndView.addObject("object", t);
    addExtraAttributeForEditPage(modelAndView);
    return modelAndView;
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public ModelAndView create(@ModelAttribute("object") T obj, HttpServletRequest request) throws FieldListException {
    getCrudService().create(obj);
    return new ModelAndView("redirect:" + getControllerUrl() + "/list");
  }

  /**
   * common method to go to specific page
   * 
   * @param pageUrl the url of the page
   * @return
   */
  @RequestMapping(value = "/page/{pageUrl}", method = RequestMethod.GET)
  public ModelAndView toPage(@PathVariable String pageUrl) {
    return new ModelAndView(pageUrl);
  }

  protected ModelAndView redirectTo(String pageName) {
    return new ModelAndView("redirect:" + pageName);
  }

  protected ModelAndView forwardTo(String pageName) {
    return new ModelAndView("forward:" + pageName);
  }

  protected UserDto getUser() {
    return (UserDto) session.getAttribute(DietConstants.SESSION_USER);
  }

  protected abstract String getMainPage();

  protected abstract String getControllerUrl();

  protected abstract List<T> getListObjects();

  public abstract IDietCrudService<T> getCrudService();

  protected abstract void addExtraAttributeForCreatePage(ModelAndView modelAndView);

  protected abstract void addExtraAttributeForEditPage(ModelAndView modelAndView);
}
