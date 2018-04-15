package com.mnan2c.diet.controller.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mnan2c.diet.controller.rest.dto.AbstractDto;
import com.mnan2c.diet.domain.AbstractDomain;
import com.mnan2c.diet.exceptions.FieldListException;
import com.mnan2c.diet.service.AbstractService;
import com.mnan2c.diet.utils.HeaderUtil;
import com.mnan2c.diet.utils.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ACrudController<E extends AbstractDomain, DTO extends AbstractDto> {

	@Inject
	private AbstractService<E, DTO> service;

	@PostMapping
	public ResponseEntity<DTO> create(@Valid @RequestBody DTO dto) throws FieldListException, URISyntaxException {
		log.debug("request to save entity [{}]", dto);
		DTO result = service.create(dto);
		return ResponseEntity //
				.created(new URI(getApiRootPath() + dto.getId())) //
				.headers(HeaderUtil.creationAlert(getAlertEntityName(), dto.getId())) //
				.body(result);
	}

	@PutMapping
	public ResponseEntity<DTO> update(@Valid @RequestBody DTO dto) throws FieldListException {
		log.debug("request to update entity [{}]", dto);
		DTO result = service.update(dto);
		return ResponseEntity.ok() //
				.headers(HeaderUtil.updateAlert(getAlertEntityName(), dto.getId())) //
				.body(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		log.debug("request to delete entity by id [{}]", id);
		service.deleteById(id);
		return ResponseEntity.ok() //
				.headers(HeaderUtil.deletionAlert(getAlertEntityName(), id)) //
				.build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<DTO> getById(@PathVariable String id) {
		log.debug("request to get entity by id [{}]", id);
		DTO result = service.getById(id);
		return Optional.ofNullable(result) //
				.map(resultDto -> new ResponseEntity<>(result, HttpStatus.OK))//
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping
	public ResponseEntity<List<DTO>> getAll() throws URISyntaxException {
		log.debug("request to get all entities.");
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		Page<DTO> page = service.getPage(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, getApiRootPath());
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	abstract protected String getAlertEntityName();

	abstract protected String getApiRootPath();
}
