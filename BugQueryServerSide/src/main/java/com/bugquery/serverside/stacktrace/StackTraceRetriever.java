package com.bugquery.serverside.stacktrace;

import java.util.List;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.exceptions.GeneralDBException;

public interface StackTraceRetriever {
	List<Post> getMostRelevantPosts(String stackTrace, int numOfPosts) throws GeneralDBException;
}
