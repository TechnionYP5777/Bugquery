package com.bugquery.serverside.webapp;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.stacktrace.StackTraceDistancer;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;
import com.bugquery.serverside.stacktrace.WeightLinesSTDistancer;

@Controller
public class SearchController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model m) {
        m.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String numResults(@RequestParam(value="trace", required=true) String trace, Model m) {
    	List<Post> $ = getResults(trace);
    	m.addAttribute("trace", trace);
    	m.addAttribute("results", $);
    	return "result";
    }
    
	List<Post> databaseStub = Arrays.asList(new Post("1", "This is a post!") {}, 
			new Post("2", "!tsop a si sihT") {});
	StackTraceDistancer distancer = new WeightLinesSTDistancer();

	
	public List<Post> getResults(String trace) {
		return StackTraceRetriever.getMostRelevantStackTraces(databaseStub, trace, distancer, 2);
	}
}
