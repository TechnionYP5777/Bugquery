import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yonzarecki
 * @since 28.11.16
 */
public class JaccardSTComparator implements StackTraceComparator {

    /**
     * @param s Stack trace string with newlines
     * @return List<String> with each String containing 2 lines
     */
    private Set<String> extractLineTuples(String s) {
        List<String> lines = StackTraceComparator.splitByNewlines(s);
        HashSet<String> dbLines = new HashSet<>(); // retval
        for (int i = 0; i < s.length(); i+=2) { // concat each 2 strings
            if (i + 1 < s.length())  // indexes in/out_of range
                dbLines.add(lines.get(i) + lines.get(i+1));
            else
                dbLines.add(lines.get(i));
        }
        return dbLines;
    }

    /**
     * jaccard distance, throws away order between lines
     * @return returns the jaccard distance, (union_size - intersection_size)
     */
    @Override
    public int compare(String o1, String o2) {
        Set<String> union = extractLineTuples(o1);
        union.addAll(extractLineTuples(o2));

        Set<String> intersection = extractLineTuples(o1);
        intersection.retainAll(extractLineTuples(o2)); // keeps only the intersection of the two

        return union.size() - intersection.size(); // union_size - (size of intersection)
    }
}