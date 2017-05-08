package com.bugquery.serverside.usecases;

import java.util.*;

import com.bugquery.serverside.dbparsing.dbretrieval.SQLDBConnector;
import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.exceptions.GeneralDBException;
import com.bugquery.serverside.exceptions.InvalidStackTraceException;
import com.bugquery.serverside.stacktrace.GeneralStackTraceRetriever;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;
import com.bugquery.serverside.stacktrace.distance.*;
import com.bugquery.serverside.stacktrace.distance.levenshtein.LevenshteinSTDistancer;

/**
 * Class used to demonstrate different use cases, 
 * and seeing the result returned from the server 
 * using the different distancers 
 * @author yonzarecki
 * @since 8.5.2017
 */
public class UseCasesDemonstrator {
	static final int numOfPosts = 10;
	
	private static List<Post> getRelevantPosts(StackTraceDistancer d, String stackTrace) {
		StackTraceRetriever retriever = new GeneralStackTraceRetriever(d, new SQLDBConnector());
		
		try {
			return retriever.getMostRelevantPosts(stackTrace, numOfPosts);
		} catch (GeneralDBException | InvalidStackTraceException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	private static List<StackTraceDistancer> getDistancerList() {
		List<StackTraceDistancer> allDistancers = new ArrayList<StackTraceDistancer>();
		allDistancers.add(new JaccardSTDistancer());
		allDistancers.add(new LevenshteinSTDistancer());
		return allDistancers;
	}
	
	private static List<String> getStackTracesList() {
		List<String> sampleStackTraces = new ArrayList<String>();
		String s1 = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		sampleStackTraces.add(s1);
		return sampleStackTraces;
	}
	
	public static void main(String[] args) {
		List<StackTraceDistancer> allDistancers = getDistancerList();
		List<String> sampleStackTraces = getStackTracesList();
		List<Post> result;
		for(StackTraceDistancer d: allDistancers)
			for (String s : sampleStackTraces)
				result = getRelevantPosts(d, s);
	}
}
