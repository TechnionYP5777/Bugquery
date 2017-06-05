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

/**
 * 
 * @author ZivIzhar & rodedzats
 * @since 24.5.2017
 * 
 */
@Controller
public class ExamplesController {
	public static final String EXAMPLE_FORMAT = "/examples/{exclass}";
	
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
}
