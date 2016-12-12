package com.bugquery.serverside.website;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("")
public class InputResource {
	@GET
	public String getStackTrace(@QueryParam("q") String trace) {
		return businessLogicStub(trace);
	}
	
	@POST
	public String postStackTrace(String trace) {
		return businessLogicStub(trace);
	}
	
	private String businessLogicStub(String trace) {
		return trace;
	}
	
	public List<Object> getResults() {
		return Arrays.asList("one", "two", "three");
	}
}
