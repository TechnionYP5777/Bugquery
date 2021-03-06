package com.bugquery.serverside.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
