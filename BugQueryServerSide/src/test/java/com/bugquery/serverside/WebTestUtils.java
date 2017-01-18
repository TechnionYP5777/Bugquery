package com.bugquery.serverside;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;

public class WebTestUtils {

	private MockMvc mockMvc;

	public WebTestUtils(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	public void assertTitle(String pageAddress, String expectedTitle) throws Exception {
		this.mockMvc.perform(get(pageAddress)).andExpect(status().isOk())
				.andExpect(content().string(containsString("<title>" + expectedTitle + "</title>")));
	}
}
