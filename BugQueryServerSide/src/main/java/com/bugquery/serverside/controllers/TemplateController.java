package com.bugquery.serverside.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TemplateController {
	
	@RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
	public static String getHome(Model ¢) {
		¢.addAttribute("view", "home");
		¢.addAttribute("title", "Welcome to BugQuery");
		return "layout";
	}

	@RequestMapping(value = "/guide", method = RequestMethod.GET)
	public static String getGuide(Model ¢) {
		¢.addAttribute("view", "guide");
		¢.addAttribute("title", "BugQuery Essentials");
		return "layout";
	}
	
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public static String getSubmit(Model ¢) {
		¢.addAttribute("view", "submit");
		¢.addAttribute("title", "Submit to BugQuery");
		return "layout";
	}
}
