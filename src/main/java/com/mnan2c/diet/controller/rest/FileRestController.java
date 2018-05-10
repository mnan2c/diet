package com.mnan2c.diet.controller.rest;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnan2c.diet.constants.DietConstants;
import com.mnan2c.diet.controller.rest.dto.FileDto;
import com.mnan2c.diet.domain.File;
import com.mnan2c.diet.service.FileService;
import com.mnan2c.diet.utils.AliyunOSSUtil;

@RequestMapping("/api/files")
@RestController
public class FileRestController extends ACrudController<File, FileDto> {
  @Inject
  private FileService fileService;

  @Override
  protected String getAlertEntityName() {
    return "file";
  }

  @Override
  protected String getApiRootPath() {
    return "/api/files";
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable String id) {
    FileDto fileDto = fileService.findById(id);
    if (fileDto != null) {
      AliyunOSSUtil.deleteFile(DietConstants.ALIYUN_OSS_URL + fileDto.getUrl());
    }
    return super.deleteById(id);
  }
}
