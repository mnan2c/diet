package com.mnan2c.diet.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mnan2c.diet.controller.rest.dto.AbstractDto;
import com.mnan2c.diet.controller.rest.dto.FileDto;
import com.mnan2c.diet.service.FileService;
import com.mnan2c.diet.service.IDietCrudService;
import com.mnan2c.diet.utils.AliyunOSSUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/upload")
@Controller
public class UploadController extends AbstractController<AbstractDto> {
  @Inject
  private FileService fileService;

  @PostMapping
  public ModelAndView uploadBlog(MultipartFile file) {
    log.debug("============> Uploading");
    try {
      if (null != file) {
        String filename = file.getOriginalFilename();
        if (!"".equals(filename.trim())) {
          File newFile = new File(filename);
          FileOutputStream os = new FileOutputStream(newFile);
          os.write(file.getBytes());
          os.close();
          file.transferTo(newFile);
          // upload to OSS
          String ossUrl = AliyunOSSUtil.upload(newFile);
          if (StringUtils.isNotBlank(ossUrl) && getUser() != null) {
            FileDto fileDto = new FileDto();
            fileDto.setUrl(ossUrl);
            fileDto.setUserId(getUser().getId());
            fileService.create(fileDto);
          }
        }
      }
    } catch (Exception ex) {
      log.error("exception happens - [{}]", ex);
    }
    return new ModelAndView("upload/form");
  }

  @Override
  protected String getMainPage() {
    return "upload";
  }

  @Override
  protected String getControllerUrl() {
    return "/upload";
  }

  @Override
  protected List<AbstractDto> getListObjects() {
    return null;
  }

  @Override
  public IDietCrudService<AbstractDto> getCrudService() {
    return null;
  }

  @Override
  protected void addExtraAttributeForCreatePage(ModelAndView modelAndView) {

  }

  @Override
  protected void addExtraAttributeForEditPage(ModelAndView modelAndView) {

  }

}
