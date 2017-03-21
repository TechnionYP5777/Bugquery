package com.bugquery.serverside.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bugquery.serverside.WebTestUtils;

/**
 * 
 * @author Amit
 * @since Jan 18, 2017
 * 
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TemplateController.class)
public class TemplateControllerTest {

	@Autowired
	private MockMvc mockMvc;
	private static final Map<String, String> pageAddressByName = createPagesMap();
	private WebTestUtils utils;
	
	private static Map<String, String> createPagesMap() {
		Map<String, String> $ = new HashMap<>();
		$.put("main", "/");
		$.put("index", "/index");
		$.put("guide", "/guide");
		$.put("submit", "/submit");
		return $;
	}
	
	@Before
	public void init() {
		utils = new WebTestUtils(mockMvc);
	}
	
	@Test
	public void statusShouldBeOk() throws Exception {
		for (String page : pageAddressByName.values())
			utils.assertStatus(page, HttpStatus.OK);
	}

	@Test
	public void mediaTypeShouldBeHtmlUtf8() throws Exception {
		for (String page : pageAddressByName.values())
			utils.assertMediaType(page, new MediaType(MediaType.TEXT_HTML, StandardCharsets.UTF_8));
	}

	@Test
	public void titleShouldBeWelcome() throws Exception {
		for (String pageName : Arrays.asList("main", "index"))
			utils.assertTitle(pageAddressByName.get(pageName), "Welcome to BugQuery");
	}

	@Test
	public void titleShouldBeEssentials() throws Exception {
		utils.assertTitle(pageAddressByName.get("guide"), "BugQuery Essentials");
	}

	@Test
	public void titleShouldBeSubmit() throws Exception {
		utils.assertTitle(pageAddressByName.get("submit"), "Submit to BugQuery");
	}
}