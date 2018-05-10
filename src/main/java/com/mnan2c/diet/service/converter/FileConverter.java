package com.mnan2c.diet.service.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.mnan2c.diet.controller.rest.dto.FileDto;
import com.mnan2c.diet.domain.File;

@Component
public class FileConverter extends AbstractConverter<File, FileDto> {

  @Override
  public File dtoToEntity(FileDto dto) {
    if (dto == null) {
      return null;
    }
    File entity = new File();
    BeanUtils.copyProperties(dto, entity);
    return entity;
  }

  @Override
  public FileDto entityToDto(File entity) {
    if (entity == null) {
      return null;
    }
    FileDto dto = new FileDto();
    copyEntityToDto(entity, dto);
    return dto;
  }

  @Override
  protected void copyEntityToDto(File entity, FileDto dto) {
    BeanUtils.copyProperties(entity, dto, "lastModifiedDate");
  }
}
