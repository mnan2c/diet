package com.mnan2c.diet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mnan2c.diet.constants.DietConstants;
import com.mnan2c.diet.controller.rest.dto.FileDto;
import com.mnan2c.diet.service.FileService;
import com.mnan2c.diet.service.IDietCrudService;

@RequestMapping("/files")
@Controller
public class FileController extends AbstractController<FileDto> {

  @Inject
  private FileService fileService;

  @GetMapping
  @ResponseBody
  public Map<String, Object> getFiles() {
    Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, new Sort(Direction.DESC, "createdDate"));
    List<FileDto> fileDtos = fileService.findPage(pageable).getContent() //
        .stream() //
        .map(file -> {
          file.setDisplayUrl(
              StringUtils.isNotBlank(file.getUrl()) ? file.getUrl().substring(38, file.getUrl().length()) : "");
          file.setCreatedDateString(DietConstants.WEB_DATETIME_FORMAT.format(file.getCreatedDate()));
          return file;
        }).collect(Collectors.toList());
    Map<String, Object> fileMap = new HashMap<>();
    fileMap.put("data", fileDtos);
    return fileMap;
  }

  @Override
  protected String getMainPage() {
    return "upload";
  }

  @Override
  protected String getControllerUrl() {
    return "/files";
  }

  @Override
  protected List<FileDto> getListObjects() {
    return null;
  }

  @Override
  public IDietCrudService<FileDto> getCrudService() {
    return fileService;
  }

  @Override
  protected void addExtraAttributeForCreatePage(ModelAndView modelAndView) {

  }

  @Override
  protected void addExtraAttributeForEditPage(ModelAndView modelAndView) {

  }

}
