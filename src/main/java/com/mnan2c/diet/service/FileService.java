package com.mnan2c.diet.service;

import org.springframework.stereotype.Service;

import com.mnan2c.diet.controller.rest.dto.FileDto;
import com.mnan2c.diet.domain.File;

@Service
public class FileService extends AbstractService<File, FileDto> {

  @Override
  protected String getDtoName() {
    return "fileDto";
  }

}
