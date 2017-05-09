package com.bugquery.serverside.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 
 * @author Amit
 * @since May 05, 2017
 * 
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
public class ExceptionHandlingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SearchController controller;

	@Test
	@SuppressWarnings("boxing")
	public void searchIdShouldNotBeFound() throws Exception {
		Exception exception = new RuntimeException("Test"); 
		Mockito.when(controller.getSearchResults(anyLong(), any()))
				.thenThrow(exception);

		mockMvc.perform(get("/stacks/1"))
				.andDo(print())
				.andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(content().string(containsString(exception + "")));
	}
}