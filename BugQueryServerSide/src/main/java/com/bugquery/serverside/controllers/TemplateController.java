package com.bugquery.serverside.controllers;

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

	@RequestMapping(value = ("/" + GUIDE), method = RequestMethod.GET)
	public static String getGuide(Model ¢) {
		¢.addAttribute(VIEW, GUIDE);
		¢.addAttribute(TITLE, "BugQuery Essentials");
		return LAYOUT;
	}

	@RequestMapping(value = { "/" + INDEX, ROOT }, method = RequestMethod.GET)
	public static String getHome(Model ¢) {
		¢.addAttribute(VIEW, HOME);
		¢.addAttribute(TITLE, "Welcome to BugQuery");
		return LAYOUT;
	}

	@RequestMapping(value = ("/" + "submit"), method = RequestMethod.GET)
	public static String getSubmit(Model ¢) {
		¢.addAttribute(VIEW, SUBMIT);
		¢.addAttribute(TITLE, "Submit to BugQuery");
		return LAYOUT;
	}
}
