package com.bugquery.serverside.stacktrace;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yonzarecki
 * @since 28.11.16
 */
public class JaccardSTDistancer implements StackTraceDistancer {

    /**
     * @param s Stack trace string with newlines
     * @return List<String> with each String containing 2 lines
     */
    private static Set<String> extractLineTuples(String s) {
        List<String> lines = StackTraceDistancer.splitByNewlines(s);
        HashSet<String> $ = new HashSet<>(); // retval
        for (int ¢ = 0; ¢ < lines.size(); ¢+=2)
			$.add(¢ >= lines.size() - 1 ? lines.get(¢) : lines.get(¢) + lines.get(¢ + 1));
        return $;
    }

    /**
     * @param o1 a String containing a stack-trace
     * @param o2 a String containing a stack-trace
     * jaccard distance, throws away order between lines
     * @return returns the jaccard distance, (union_size - intersection_size)
     */
    @Override
    public double distance(String o1, String o2) {
    	Set<String> line_set1 = extractLineTuples(o1);
    	Set<String> line_set2 = extractLineTuples(o2);
    	
        Set<String> $ = new HashSet<>(line_set1); // acctually this is union
        $.addAll(line_set2);

        Set<String> intersection =  new HashSet<>(line_set1);
        intersection.retainAll(line_set2); // keeps only the intersection of the two
        
        // normalize by union's size, but still keep order by the union's size
        return intersection.size() == $.size() ? 0.0 : 1 - (intersection.size() + 1.0) / $.size();
    }
}