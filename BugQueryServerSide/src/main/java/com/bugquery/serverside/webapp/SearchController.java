package com.bugquery.serverside.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.bugquery.serverside.webapp.StackSearch;
import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.exceptions.GeneralDBException;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;

@Controller
public class SearchController {

	// 404 exception
	// TODO: move elsewhere
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public class ResourceNotFoundException extends RuntimeException {
		private static final long serialVersionUID = -3652773574082676217L;

		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public class InternalServerError extends RuntimeException {
		private static final long serialVersionUID = 2560023147133737591L;

		public InternalServerError(String message) {
			super(message);
		}

		public InternalServerError(Exception e) {
			super(e.getMessage());
		}
	}

	@Autowired
	private StackSearchRepository repository;

	@RequestMapping(value = "/stacks/{id}", method = RequestMethod.GET)
	public String getSearchResults(@PathVariable Long id, Model m) {
		StackSearch ss = repository.findOne(id);
		if (ss == null)
			throw new ResourceNotFoundException("Couldn't find search id " + id);
		String trace = ss.getTrace();
		List<Post> $;
		try {
			$ = getResults(trace);
		} catch (GeneralDBException e) {
			throw new InternalServerError(e);
		}
		m.addAttribute("trace", trace);
		m.addAttribute("results", $);
		return "result";
	}

	@RequestMapping(value = "/stacks", method = RequestMethod.POST)
	public String addStackSearch(@RequestBody String input) {
		String traceFormKey = "trace="; // TODO: Amit & Yosef, decide on a
										// uniform representation for website
										// form and plugin
		String trace = !input.startsWith(traceFormKey) ? input : input.substring(traceFormKey.length());
		StackSearch $ = new StackSearch(trace);
		repository.save($);
		return "redirect:stacks/" + $.getId();
	}

	public static List<Post> getResults(String trace) throws GeneralDBException {
		return StackTraceRetriever.getMostRelevantPosts(trace, 10);
	}

	public void setRepository(StackSearchRepository ¢) {
		this.repository = ¢;
	}
}
