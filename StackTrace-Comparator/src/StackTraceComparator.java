import java.util.Comparator;

/**
 * @author yonzarecki
 * @since 28.11.16
 * This interface should make the code clearer, as it sits above specific stack-trace comparison algorithms
 */
public interface StackTraceComparator extends Comparator<String> {

}
