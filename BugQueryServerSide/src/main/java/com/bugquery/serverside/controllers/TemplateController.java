package com.bugquery.serverside.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Hooks to handle miscellaneous requests
 * 
 * @author Yosef
 * @since Dec 28, 2016
 *
 */
@Controller
public class TemplateController {

	private static final String INDEX = "index";
	private static final String GUIDE = "guide";
	private static final String HOME = "home";
	private static final String LAYOUT = "layout";
	private static final String ROOT = "/";
	private static final String SUBMIT = "submit";
	private static final String TITLE = "title";
	private static final String VIEW = "view";
	private static final String DESCRIPTION = "description";
	private static final String EXAMPLES = "examples";
	private static final String EXTYPELIST = "extypelist";
	
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
		List<String> l = new ArrayList<>();
		//Temp list adding, will fix to the real exception type list
		l.add("NullPointer");
		l.add("Ziv izhar");
		¢.addAttribute(EXTYPELIST, l);
		return LAYOUT;
	}
}
