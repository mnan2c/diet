package com.mnan2c.diet.service.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public abstract class AbstractConverter<E, D> {

	public abstract E dtoToEntity(D dto);

	public abstract D entityToDto(E entity);

	public List<D> entitiesToDtos(List<E> entities) {
		return entities.stream() //
				.map(entity -> entityToDto(entity)) //
				.collect(Collectors.toList());
	}

	public List<E> dtosToEntities(List<D> dtos) {
		return dtos.stream() //
				.map(entity -> dtoToEntity(entity)) //
				.collect(Collectors.toList());
	}

	public Page<D> getDtoPages(Page<E> page, Pageable pageable) {
		List<D> dtos = entitiesToDtos(page.getContent());
		PageImpl<D> result = new PageImpl<D>(dtos, pageable, page.getTotalElements());
		return result;
	}

	protected void copyEntityToDto(E entity, D dto) {
		BeanUtils.copyProperties(entity, dto, "createdDate", "lastModifiedDate");
	}
}
