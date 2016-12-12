package org.Website;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("")
public class InputResource {
	@GET
	public String getStackTrace(@QueryParam("trace") String trace) {
		return businessLogicStub(trace);
	}
	
	@POST
	public String postStackTrace(String trace) {
		return businessLogicStub(trace);
	}
	
	private String businessLogicStub(String trace) {
		return trace;
	}
}
