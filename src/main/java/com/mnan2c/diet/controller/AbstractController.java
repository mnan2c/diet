package com.mnan2c.diet.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mnan2c.diet.controller.rest.dto.AbstractDto;
import com.mnan2c.diet.exceptions.FieldListException;
import com.mnan2c.diet.service.IDietCrudService;


public abstract class AbstractController<T extends AbstractDto> {

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
    return modelAndView;
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public ModelAndView create(@ModelAttribute("object") T obj, HttpServletRequest request) throws FieldListException {
    getCrudService().create(obj);
    return new ModelAndView("redirect:" + getControllerUrl() + "/list");
  }

  protected abstract String getMainPage();

  protected abstract String getControllerUrl();

  protected abstract List<T> getListObjects();

  public abstract IDietCrudService<T> getCrudService();
}
