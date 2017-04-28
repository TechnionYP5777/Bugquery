package com.bugquery.serverside.stacktrace.distance;

import java.util.List;

/**
 * @author rodedzats
 * @since 28.11.16
 */
public class WeightLinesSTDistancer implements StackTraceDistancer {
	/**
     * @return returns weighted lines distance between 2 stack traces. simple implementation
     */
    @Override
    public double distance(String o1, String o2) {
    	List<String> st1 = StackTraceDistancer.splitByNewlines(o1);
    	List<String> st2 = StackTraceDistancer.splitByNewlines(o2);
    	double currentWeight = 1;
    	double $ = 0;
    	for(int ¢=0; ¢ < Math.max(st1.size(), st2.size()); ++¢) {
    		if(¢ >= st1.size() || ¢ >= st2.size() || 
    				!st1.get(¢).equalsIgnoreCase(st2.get(¢)))
				$ += currentWeight;
    		currentWeight *= 0.9;
    	}
    	return $;
    }
}
