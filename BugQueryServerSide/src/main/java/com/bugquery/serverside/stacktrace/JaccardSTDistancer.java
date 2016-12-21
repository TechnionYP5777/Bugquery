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
    private Set<String> extractLineTuples(String s) {
        List<String> lines = StackTraceDistancer.splitByNewlines(s);
        HashSet<String> $ = new HashSet<>(); // retval
        for (int ¢ = 0; ¢ < lines.size(); ¢+=2)
			$.add(¢ + 1 >= lines.size() ? lines.get(¢) : lines.get(¢) + lines.get(¢ + 1));
        return $;
    }

    /**
     * jaccard distance, throws away order between lines
     * @return returns the jaccard distance, (union_size - intersection_size)
     */
    @Override
    public double distance(String o1, String o2) {
        Set<String> union = extractLineTuples(o1);
        union.addAll(extractLineTuples(o2));

        Set<String> intersection = extractLineTuples(o1);
        intersection.retainAll(extractLineTuples(o2)); // keeps only the intersection of the two

        return union.size() - intersection.size(); // union_size - (size of intersection)
    }
}