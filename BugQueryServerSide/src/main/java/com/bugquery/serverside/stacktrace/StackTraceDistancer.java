package com.bugquery.serverside.stacktrace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bugquery.serverside.entities.StackTrace;

/**
 * @author yonzarecki
 * @since 28.11.16
 * This interface should make the code clearer, as it sits above specific stack-trace comparison algorithms
 */
public interface StackTraceDistancer {
	
	double distance(String o1, String o2);
	
	default double distance(StackTrace st1, StackTrace st2) {
		return this.distance(st1.getString(), st2.getString());
	}
	
	static List<String> splitByNewlines(String ¢) {
        return ¢ == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(¢.split("[\\r\\n]+")));
    }
}