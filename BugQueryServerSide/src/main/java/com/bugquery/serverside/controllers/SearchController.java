package com.bugquery.serverside.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.StackSearch;
import com.bugquery.serverside.entities.StackSearch.Status;
import com.bugquery.serverside.exceptions.InvalidStackTraceException;
import com.bugquery.serverside.exceptions.ResourceNotFoundException;
import com.bugquery.serverside.repositories.StackSearchRepository;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;

/**
 * Hook to handle {@link #REQUEST_FORMAT}
 * 
 * @author AmitOhayon
 * @since 24.12.2016
 * Controller for stack searches
 * 
 */
@Controller
public class SearchController {

	private static final String TRACE_KEY = "trace=";
	private static final String SKIP_LOADING_KEY = "skiploading=";

	public static final String REQUEST_FORMAT = "/stacks/{id}";
	@Autowired
	private StackSearchRepository repository;

	@Autowired
	private StackTraceRetriever retriever;

	/**
	 * Return search results. If searched before - use cached results, otherwise
	 * calculate from scratch.
	 * 
	 * @param id Search id
	 * @param m Model
	 * @return Result page
	 */
	@RequestMapping(value = REQUEST_FORMAT, method = RequestMethod.GET)
	public String getSearchResults(@PathVariable Long id, Model m) throws InvalidStackTraceException {
		StackSearch ss = repository.findOne(id);
		if (ss == null)
			throw new ResourceNotFoundException("Search id \"" + id + "\" could not be found.");
		if (ss.status == Status.NEW) {
			ss.getReady(retriever);
			repository.save(ss);
		}
		m.addAttribute("trace", ss.getTrace());
		m.addAttribute("results", getResults(ss));
		return "result";
	}

	/**
	 * Post a new stack search. 
	 * Redirects to a loading page unless "skip loading" flag is set.
	 *  
	 * @param input POST method input
	 * @param m Model
	 * @return Loading page or redirect to stack search page
	 */
	@RequestMapping(value = "/stacks", method = RequestMethod.POST)
	public String addStackSearch(@RequestBody String input, Model m) throws InvalidStackTraceException {
		String trace = input.substring(input.indexOf(TRACE_KEY) + TRACE_KEY.length());
		Long stackId = repository.save(new StackSearch(trace)).getId();
		if (shouldSkipLoading(input))
			return "redirect:stacks/" + stackId;

		m.addAttribute("stackId", stackId);
		return "loading";
	}

	/**
	 * Check if skip loading flag is set.
	 * 
	 * @param input POST method input
	 * @return Is skip loading flag set
	 */
	private static boolean shouldSkipLoading(String input) {
		int skipLoadingIdx = input.indexOf(SKIP_LOADING_KEY);
		return skipLoadingIdx != -1 && skipLoadingIdx < input.indexOf(TRACE_KEY)
				&& input.substring(skipLoadingIdx + SKIP_LOADING_KEY.length()).startsWith("true");
	}

	public List<Post> getResults(StackSearch s) {
		return retriever.getPostsByIds(s.getRelatedPostsIds());
	}
}
