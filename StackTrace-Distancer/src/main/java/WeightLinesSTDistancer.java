import java.util.List;

/**
 * @author rodedzats
 * @since 28.11.16
 */
public class WeightLinesSTDistancer implements StackTraceDistancer {
	/**
     * @return returns weighted lines distance between 2 stack traces. simple implementation
     * TODO: check effectiveness.
     */
    @Override
    public double distance(String o1, String o2) {
    	List<String> st1 = StackTraceDistancer.splitByNewlines(o1);
    	List<String> st2 = StackTraceDistancer.splitByNewlines(o2);
    	double currentWeight = 1;
    	double currentDistance = 0;
    	for(int i=0; i < Math.max(st1.size(), st2.size()); i++) {
    		if(i >= st1.size() || i >= st2.size() || 
    				!st1.get(i).equalsIgnoreCase(st2.get(i))) {
    			currentDistance += currentWeight;
    		}
    		//TODO: decide how to choose reducing parameter
    		currentWeight = currentWeight * 0.9;
    	}
    	return currentDistance;
    }
}
