package com.bugquery.serverside.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bugquery.serverside.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionHandlingController {
	
	private static String traceToString(StackTraceElement[] elements){
		String $ = "";
		for(StackTraceElement elm : elements){
			$ += elm.toString() + "\n";
		}
		return $;
	}

	/*
	 * Handles com.bugquery.serverside.exceptions.ResourceNotFoundException,
	 * Assumes Exception.getMessage() has all the relevant information.
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public static String ResourceNotFoundHandler(Exception x, Model ¢) {
		¢.addAttribute("exception", x.getMessage());
		¢.addAttribute("title", "BugQuery - Resource Not Found");
		return "error";
	}

	
	/*
	 * Default handler, deals with all other, non-specific, types of Exception
	 */
	@ExceptionHandler(Exception.class)
	public static String defaultErrorHandler(HttpServletRequest r, Exception x, Model ¢) throws Exception {
		if (AnnotationUtils.findAnnotation(x.getClass(), ResponseStatus.class) != null)
			throw x;
		¢.addAttribute("exception", x + "");
		¢.addAttribute("title", (r.getRequestURL() + ""));
		
		// for debugging purposes, we'll also include stack trace at this time:
		¢.addAttribute("trace", traceToString(x.getStackTrace()));
		return "error";
	}

}