package com.bugquery.serverside.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {
	private static final long serialVersionUID = 2560023147133737591L;

	public InternalServerException(String message) {
		super(message);
	}

	public InternalServerException(Exception e) {
		super(e.getMessage());
	}
}