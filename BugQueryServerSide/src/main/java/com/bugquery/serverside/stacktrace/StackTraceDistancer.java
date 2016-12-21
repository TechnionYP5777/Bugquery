package com.bugquery.serverside.stacktrace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yonzarecki
 * @since 28.11.16
 * This interface should make the code clearer, as it sits above specific stack-trace comparison algorithms
 */
public interface StackTraceDistancer {
	
	double distance(String o1, String o2);
	
	static List<String> splitByNewlines(String ¢) {
        return ¢ == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(¢.split("[\\r\\n]+")));
    }
}