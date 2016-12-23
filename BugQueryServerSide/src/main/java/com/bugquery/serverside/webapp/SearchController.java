package com.bugquery.serverside.webapp;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bugquery.serverside.webapp.StackSearch;
import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.PostStub;
import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.stacktrace.StackTraceDistancer;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;
import com.bugquery.serverside.stacktrace.WeightLinesSTDistancer;

@Controller
public class SearchController {

	@Autowired
	private StackSearchRepository repository;

	@RequestMapping(value = "/stacks/{id}", method = RequestMethod.GET)
	public String getSearchResults(@PathVariable Long id, Model m) {
		String trace = repository.findOne(id).getTrace();
		List<Post> $ = getResults(trace);
		m.addAttribute("trace", trace);
		m.addAttribute("results", $);
		return "result";
	}

	@RequestMapping(value = "/stacks", method = RequestMethod.POST)
	public String addStackSearch(@RequestBody String input) {
		String traceFormKey = "trace="; //TODO: Amit & Yosef, decide on a  
		                                //uniform representation for website form and plugin
		String trace = !input.startsWith(traceFormKey) ? input : input.substring(traceFormKey.length());
		StackSearch $ = new StackSearch(trace);
		repository.save($);
		return "redirect:stacks/" + $.getId();
	}

	List<Post> databaseStub = Arrays.asList(new PostStub("This is a post!"), new PostStub("!tsop a si sihT"));
	StackTraceDistancer distancer = new WeightLinesSTDistancer();

	public List<Post> getResults(String trace) {
		return StackTraceRetriever.getMostRelevantStackTraces(databaseStub, new StackTrace(trace), distancer, 2);
	}

	public void setRepository(StackSearchRepository ¢) {
		this.repository = ¢;
	}
}
