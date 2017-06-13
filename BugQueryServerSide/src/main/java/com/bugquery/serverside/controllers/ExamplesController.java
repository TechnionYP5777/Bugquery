package com.bugquery.serverside.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.examples.ExamplesParser;
import com.bugquery.serverside.exceptions.GeneralDBException;
import com.bugquery.serverside.repositories.PostRepository;
import com.bugquery.serverside.repositories.StackSearchRepository;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;

/**
 * This controller is used for the examples pages and for the "all traces" pages.
 * @author ZivIzhar & rodedzats
 * @since 24.5.2017
 * 
 */
@Controller
public class ExamplesController {
	private static final String ALLTRACES_RESULTS = "alltraces_results";
	private static final String EXAMPLES = "examples";
	private static final String ALLTRACES = "alltraces";
	private static final String EXTYPELIST = "extypelist";
	public static final String EXAMPLE_FORMAT = "/examples/{exclass}";
	public static final String ALLTRACES_FORMAT = "alltraces/{exclass}";
	
	@Autowired
	private PostRepository repository;
	
	@RequestMapping(value = EXAMPLE_FORMAT, method = RequestMethod.GET)
	public static String getExampleClass(@PathVariable String exclass, Model m) {
		List<Post> $ = new ArrayList<>();
		ExamplesParser e = new ExamplesParser();
		$ = e.getPosts(exclass);
		m.addAttribute("exceptiontype", exclass);
		m.addAttribute("trace", e.getStackTraceByExceptionType(exclass));
		m.addAttribute("results", $);
		m.addAttribute(TemplateController.DESCRIPTION, "Example search result for a " +
		"stacktrace from the type: " + exclass);
		return "exampleresult";
	}
	
	@RequestMapping(value = ALLTRACES_FORMAT, method = RequestMethod.GET)
	public String getAllTracesOfType(@PathVariable String exclass, Model m) {
		String exceptionType = exclass.replaceAll("-", "\\.");
		m.addAttribute(TemplateController.VIEW, ExamplesController.ALLTRACES_RESULTS);
		m.addAttribute(TemplateController.TITLE, "All Traces");
		List<Post> $ = repository.findByStackTraceException(exceptionType);
		m.addAttribute("exceptiontype", exceptionType);
		m.addAttribute("results", $);
		m.addAttribute(TemplateController.DESCRIPTION, "All stack traces matching " + exceptionType + 
				" exception type");
		return TemplateController.LAYOUT;
	}
	
	@RequestMapping(value = ("/" + "examples"), method = RequestMethod.GET)
	public static String getExamples(Model ¢) {
		¢.addAttribute(TemplateController.VIEW, EXAMPLES);
		¢.addAttribute(TemplateController.TITLE, "Examples");
		¢.addAttribute(EXTYPELIST, new ExamplesParser().getExceptionTypes());
		¢.addAttribute(TemplateController.DESCRIPTION, "Examples page with stacktraces for " +
				"the most common Java exception types: java.lang.NullPointerException," +
				 " java.lang.NumberFormatException, " +
				"java.lang.IllegalArgumentException, java.lang.RuntimeException, java.lang.IllegalStateException");
		return TemplateController.LAYOUT;
	}
	
	@RequestMapping(value = ("/" + "alltraces"), method = RequestMethod.GET)
	public String getAllTraces(Model ¢) {
		¢.addAttribute(TemplateController.VIEW, ALLTRACES);
		¢.addAttribute(TemplateController.TITLE, "Available Exception Types");
		¢.addAttribute(TemplateController.DESCRIPTION, "All Java stack traces from all exception types in our database");
		List<String> l = repository.findDistinctExceptions();
		¢.addAttribute(EXTYPELIST, l);
		return TemplateController.LAYOUT;
	}
}
