package com.bugquery.serverside.website;

import java.util.Arrays;
import java.util.List;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.stacktrace.StackTraceDistancer;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;
import com.bugquery.serverside.stacktrace.WeightLinesSTDistancer;

public class Controller {
	List<Post> databaseStub = Arrays.asList(new Post("1", "This is a post!") {}, 
			new Post("2", "!tsop a si sihT") {});
	StackTraceDistancer distancer = new WeightLinesSTDistancer();

	public List<Post> getResults(String trace) {
		return StackTraceRetriever.getMostRelevantStackTraces(databaseStub, trace, distancer, 2);
	}
}
