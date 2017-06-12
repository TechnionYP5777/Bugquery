package com.bugquery.serverside.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bugquery.serverside.examples.ExamplesParser;

/**
 * Hooks to handle miscellaneous requests
 * 
 * @author yosefraisman
 * @since 28.12.2016
 *
 */
@Controller
public class TemplateController {

	private static final String INDEX = "index";
	private static final String GUIDE = "guide";
	private static final String HOME = "home";
	private static final String ROOT = "/";
	private static final String SUBMIT = "submit";
	private static final String EXAMPLES = "examples";
	private static final String ALLTRACES = "alltraces";
	private static final String EXTYPELIST = "extypelist";
	public static final String DESCRIPTION = "description";
	public static final String VIEW = "view";
	public static final String LAYOUT = "layout";
	public static final String TITLE = "title";
	
	@RequestMapping(value = ("/" + GUIDE), method = RequestMethod.GET)
	public static String getGuide(Model ¢) {
		¢.addAttribute(VIEW, GUIDE);
		¢.addAttribute(TITLE, "BugQuery Essentials");
		¢.addAttribute(DESCRIPTION, "This guide provides the essential information you need in " + 
				"order to debug your code with BugQuery");
		return LAYOUT;
	}

	@RequestMapping(value = { "/" + INDEX, ROOT }, method = RequestMethod.GET)
	public static String getHome(Model ¢) {
		¢.addAttribute(VIEW, HOME);
		¢.addAttribute(TITLE, "Welcome to BugQuery");
		¢.addAttribute(DESCRIPTION, "BugQuery is a search engine for stack traces. It helps " + 
				"programmers solve bugs in their code in a quick and efficient way.");
		return LAYOUT;
	}

	@RequestMapping(value = ("/" + "submit"), method = RequestMethod.GET)
	public static String getSubmit(Model ¢) {
		¢.addAttribute(VIEW, SUBMIT);
		¢.addAttribute(TITLE, "Submit to BugQuery");
		¢.addAttribute(DESCRIPTION, "Submit your stack trace to BugQuery and get quick solution " + 
		"for your bugs.");
		return LAYOUT;
	}
	
	@RequestMapping(value = ("/" + "examples"), method = RequestMethod.GET)
	public static String getExamples(Model ¢) {
		¢.addAttribute(VIEW, EXAMPLES);
		¢.addAttribute(TITLE, "Examples");
		¢.addAttribute(EXTYPELIST, new ExamplesParser().getExceptionTypes());
		¢.addAttribute(DESCRIPTION, "Examples page with stacktraces for " +
				"the most common Java exception types: java.lang.NullPointerException," +
				 " java.lang.NumberFormatException, " +
				"java.lang.IllegalArgumentException, java.lang.RuntimeException, java.lang.IllegalStateException");
		return LAYOUT;
	}
	
	@RequestMapping(value = ("/" + "alltraces"), method = RequestMethod.GET)
	public static String getAllTraces(Model ¢) {
		¢.addAttribute(VIEW, ALLTRACES);
		¢.addAttribute(TITLE, "Available Exception Types");
		¢.addAttribute(DESCRIPTION, "All Java stack traces from all exception types in our database");
		List<String> l = new ArrayList<>();
		l.add("java.lang.NullPointerException");
		l.add("java.lang.NumberFormatException");
		¢.addAttribute(EXTYPELIST, l);
		return LAYOUT;
	}
}
