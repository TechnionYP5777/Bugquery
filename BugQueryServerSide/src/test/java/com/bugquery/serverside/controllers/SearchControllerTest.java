package com.bugquery.serverside.controllers;

import java.net.URLEncoder;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bugquery.serverside.WebTestUtils;
import com.bugquery.serverside.entities.StackSearch;
import com.bugquery.serverside.repositories.StackSearchRepository;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;

/**
 * 
 * @author Amit
 * @since Apr 27, 2017
 * 
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
public class SearchControllerTest {

	@Autowired
	private MockMvc mockMvc;
	private WebTestUtils utils;

	@MockBean
	private StackSearchRepository repo;
	@MockBean
	private StackTraceRetriever retriever;

	private static String trace = "Exception in thread \"main\" java.lang.NullPointerException\n"
			+ "        at com.example.myproject.Book.getTitle(Book.java:16)\n"
			+ "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
			+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";

	Long id = Long.valueOf(1L);

	@Before
	public void init() {
		utils = new WebTestUtils(mockMvc);
	}

	@Test
	public void searchIdShouldNotBeFound() throws Exception {
		Mockito.when(repo.findOne(id)).thenReturn(null);
		utils.assertContentContains("/stacks/" + id, "Search id &quot;" + id + "&quot; could not be found.");
	}

	@Test
	public void searchShouldExist() throws Exception {
		Mockito.when(repo.findOne(id)).thenReturn(new StackSearch(URLEncoder.encode(trace, "UTF-8")));
		Mockito.when(retriever.getMostRelevantPosts(Matchers.any(), Matchers.anyInt())).thenReturn(new ArrayList<>());
		utils.assertStatus("/stacks/" + id, HttpStatus.OK);
	}
}