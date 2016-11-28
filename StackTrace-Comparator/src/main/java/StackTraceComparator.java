import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author yonzarecki
 * @since 28.11.16
 * This interface should make the code clearer, as it sits above specific stack-trace comparison algorithms
 */
public interface StackTraceComparator extends Comparator<String> {

    static List<String> splitByNewlines(String s) {
        return s != null ? new ArrayList<>(Arrays.asList(s.split("[\\r\\n]+"))) : new ArrayList<>();
    }
}