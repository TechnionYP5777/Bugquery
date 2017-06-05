package com.bugquery.serverside.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bugquery.serverside.exceptions.InvalidStackTraceException;
import com.bugquery.serverside.exceptions.ResourceNotFoundException;

/**
 * Sink: all exception go here
 * 
 * @author yosefraisman, AmitOhayon
 * @since 2.1.2017
 *
 */
@ControllerAdvice
public class ExceptionHandlingController {

	private static String traceToString(StackTraceElement[] es) {
		String $ = "";
		for (StackTraceElement elm : es)
			$ += elm + "\n";
		return $;
	}

	/**
	 * Handles com.bugquery.serverside.exceptions.ResourceNotFoundException,
	 * Assumes Exception.getMessage() has all the relevant information.
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public static String ResourceNotFoundHandler(Exception x, Model ¢) {
		return setupErrorPage(x, "Resource Not Found", null, ¢);
	}

	/**
	 * Handles invalid stack traces - when exception is not found.
	 */
	@ExceptionHandler(InvalidStackTraceException.class)
	public static String InvalidStackTraceHandler(Exception x, Model ¢) {
		return setupErrorPage(x, "Invalid Stack Trace", null, ¢);
	}

	/**
	 * Default handler, deals with all other, non-specific, types of Exception
	 */
	@ExceptionHandler(Exception.class)
	public static String defaultErrorHandler(HttpServletRequest r, Exception x, Model ¢) throws Exception {
		if (AnnotationUtils.findAnnotation(x.getClass(), ResponseStatus.class) != null)
			throw x;
		Map<String, Object> extras = new HashMap<>();
		extras.put("errortrace", traceToString(x.getStackTrace()));
		return setupErrorPage(x, r.getRequestURL() + "", extras, ¢);
	}

	/**
	 * Setup an error page.
	 * 
	 * @param x The handled exception
	 * @param titleSuffix Page's unique title ending
	 * @param extras Map of keys and values to be added to the model
	 * @param m The page's model
	 * @return Returns error template
	 */
	private static String setupErrorPage(Exception x, String titleSuffix, Map<String, Object> extras,
			Model m) {
		m.addAttribute("exception", x.getMessage());
		m.addAttribute("title", "BugQuery - " + titleSuffix);
		if (extras != null)
			for (Map.Entry<String, Object> entry : extras.entrySet())
				m.addAttribute(entry.getKey(), entry.getValue());
		return "error";
	}

}