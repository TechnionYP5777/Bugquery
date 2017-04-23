package com.bugquery.serverside.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Amit
 * @since Dec 24, 2016
 * General internal error exception class
 * 
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {
	private static final long serialVersionUID = 0x2387077D1DAF1A77L;

	public InternalServerException(String message) {
		super(message);
	}

	public InternalServerException(Exception e) {
		super(e.getMessage());
	}
}