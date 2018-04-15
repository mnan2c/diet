package com.mnan2c.diet.exceptions;

import java.util.Arrays;
import java.util.List;

import org.springframework.validation.FieldError;

@SuppressWarnings("serial")
public class FieldListException extends Exception {

	@SuppressWarnings("unused")
	private final List<FieldError> fieldErrors;

	public FieldListException(String[] errorInfo) {
		super(errorInfo[2]);
		this.fieldErrors = Arrays.asList(new FieldError( //
				errorInfo[0], //
				errorInfo[1], //
				errorInfo[2] //
		));
	}

	/**
	 * 
	 * @param entityName
	 * @param errorInfo
	 *            include the field of the entity and the error message
	 */
	public FieldListException(String entityName, String[] errorInfo) {
		super(errorInfo[1]);
		this.fieldErrors = Arrays.asList(new FieldError( //
				entityName, //
				errorInfo[0], //
				errorInfo[1] //
		));
	}
}
