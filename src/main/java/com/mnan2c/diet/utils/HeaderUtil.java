package com.mnan2c.diet.utils;

import org.springframework.http.HttpHeaders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderUtil {

	private HeaderUtil() {
	}

	public static HttpHeaders createHeadersWithEntityName(String message, String param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Diet-Alert", message);
		headers.add("X-Diet-Params", param);
		return headers;
	}

	public static HttpHeaders createHeaders(String message) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Diet-Alert", message);
		return headers;
	}

	public static HttpHeaders creationAlert(String entityName, String param) {
		return createHeadersWithEntityName("A new " + entityName + " is created with identifier " + param, param);
	}

	public static HttpHeaders updateAlert(String entityName, String param) {
		return createHeadersWithEntityName("A " + entityName + " is updated with identifier " + param, param);
	}

	public static HttpHeaders deletionAlert(String entityName, String param) {
		return createHeadersWithEntityName("A " + entityName + " is deleted with identifier " + param, param);
	}
	
	public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
	    log.error("Entity creation failed, {}", defaultMessage);
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Diet-Error", defaultMessage);
	    headers.add("X-Diet-ErrorKey", errorKey);
	    headers.add("X-Diet-Params", entityName);
	    return headers;
	  }
}
