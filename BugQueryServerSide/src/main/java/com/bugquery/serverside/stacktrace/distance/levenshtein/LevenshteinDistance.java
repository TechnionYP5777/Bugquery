package com.bugquery.serverside.stacktrace.distance.levenshtein;

import com.bugquery.serverside.entities.StackTrace;

/**
 * This class implements the logic of the levenshtein distance calculation
 * between 2 stack traces. This class is used by LevenshteinSTDistancer.
 * The calculated distance is between the list of stack of calls in the StackTrace.
 * @author rodedzats
 * @since 29.4.2017
 */
class LevenshteinDistance {
	private StackTrace s1;
	private int size1;
	private StackTrace s2;
	private int size2;
	private LevenshteinCost cost;
	private double[][] distance;
	public LevenshteinDistance(StackTrace s1, StackTrace s2, LevenshteinCost cost) {
		this.s1 = s1;
		this.size1 = s1.getStackOfCalls().size();
		this.s2 = s2;
		this.size2 = s2.getStackOfCalls().size();
		this.cost = cost;
		this.distance = new double[size1 + 1][size2 + 1];
	}
	
	public double getDistance() {
		initializeDistances();
		fillAllDistances();
		return distance[size1][size2];
	}
	
	public double[][] getDistanceMatrix() {
		return distance;
	}
	
	public void printDistanceMatrix() {
		for(int i=0; i<=size1; ++i) {
			for(int j=0; j<=size2; ++j)
				System.out.print(String.valueOf(distance[i][j]) + " ");
			System.out.println("");
		}
	}
	
	private void initializeDistances() {
		distance[0][0] = 0;
		int min = Math.min(size1, size2);
		for(int ¢ = 1; ¢ <= min; ++¢) {
			distance[¢][0] = ¢ * cost.insert(s1, ¢);
			distance[0][¢] = ¢ * cost.insert(s2, ¢);
		}
		for(int ¢ = min + 1; ¢ <= size1; ++¢)
			distance[¢][0] = ¢ * cost.insert(s1, ¢);
		for(int ¢ = min + 1; ¢ <= size2; ++¢)
			distance[0][¢] = ¢ * cost.insert(s2, ¢);
	}
	
	private void fillAllDistances(){
		for (int i = 1; i <= size1; ++i)
			for (int j = 1; j <= size2; ++j)
				calculateDistance(i, j);
	}
	
	private void calculateDistance(int i, int j) {
		distance[i][j] = Math.min(distance[i - 1][j] + cost.delete(s1, i), Math.min(
				distance[i][j - 1] + cost.insert(s2, j), distance[i - 1][j - 1] + cost.substitute(s1, i, s2, j)));
	}
}
