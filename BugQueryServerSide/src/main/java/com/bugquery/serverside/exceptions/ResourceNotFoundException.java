package com.bugquery.serverside.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Amit
 * @since Dec 24, 2016
 * Resource not found exception class
 * 
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -3652773574082676217L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}