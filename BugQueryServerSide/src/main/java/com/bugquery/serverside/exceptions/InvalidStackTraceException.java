package com.bugquery.serverside.exceptions;

public class InvalidStackTraceException extends Exception {

	private static final long serialVersionUID = -1659846349202821309L;

	public InvalidStackTraceException(String message) {
		super(message);
	}
}
