package com.bugquery.serverside.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(TemplateController.class)
public class TemplateControllerTest {

	@Autowired
	private MockMvc mockMvc;
	private static final Map<String, String> pageAddressByName = createPagesMap();

	private static Map<String, String> createPagesMap() {
		Map<String, String> $ = new HashMap<>();
		$.put("main", "/");
		$.put("index", "/index");
		$.put("guide", "/guide");
		$.put("submit", "/submit");
		return $;
	}

	private void assertTitle(String pageName, String expectedTitle) throws Exception {
		this.mockMvc.perform(get(pageAddressByName.get(pageName))).andExpect(status().isOk())
				.andExpect(content().string(containsString("<title>" + expectedTitle + "</title>")));
	}

	@Test
	public void mediaTypeShouldBeHtmlUtf8() throws Exception {
		for (String page : pageAddressByName.values())
			this.mockMvc.perform(get(page)).andExpect(status().isOk())
					.andExpect(content().contentType(new MediaType(MediaType.TEXT_HTML, StandardCharsets.UTF_8)));
	}

	@Test
	public void titleShouldBeWelcome() throws Exception {
		for (String pageName : Arrays.asList("main", "index"))
			assertTitle(pageName, "Welcome to BugQuery");
	}

	@Test
	public void titleShouldBeEssentials() throws Exception {
		assertTitle("guide", "BugQuery Essentials");
	}

	@Test
	public void titleShouldBeSubmit() throws Exception {
		assertTitle("submit", "Submit to BugQuery");
	}
}