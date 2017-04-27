package com.bugquery.serverside;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 
 * @author Amit
 * @since Jan 18, 2017
 *
 */
public class WebTestUtils {

	private MockMvc mockMvc;

	public WebTestUtils(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	public void assertStatus(String pageAddress, HttpStatus s) throws Exception {
		this.mockMvc.perform(get(pageAddress)).andExpect(status().is(s.value()));
	}

	public void assertTitle(String pageAddress, String expectedTitle) throws Exception {
		this.mockMvc.perform(get(pageAddress))
				.andExpect(content().string(containsString("<title>" + expectedTitle + "</title>")));
	}

	public void assertMediaType(String pageAddress, MediaType t) throws Exception {
		this.mockMvc.perform(get(pageAddress)).andExpect(content().contentType(t));
	}

	public void assertContentContains(String pageAddress, String expectedContent) throws Exception {
		this.mockMvc.perform(get(pageAddress)).andExpect(content().string(containsString(expectedContent)));
	}
}
