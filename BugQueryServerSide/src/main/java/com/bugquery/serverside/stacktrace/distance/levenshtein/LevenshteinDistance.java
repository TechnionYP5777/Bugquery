package com.bugquery.serverside.stacktrace.distance.levenshtein;

import java.util.List;

import com.bugquery.serverside.entities.StackTrace;

/**
 * Calculate Levenshtein distance between 2 lists of stack of calls in StackTrace.
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
		for(int i = 1; i <= min; ++i) {
			distance[i][0] = i * cost.insert(s1, i);
			distance[0][i] = i * cost.insert(s2, i);
		}
		for(int i = min + 1; i <= size1; ++i)
			distance[i][0] = i * cost.insert(s1, i);
		for(int i = min + 1; i <= size2; ++i)
			distance[0][i] = i * cost.insert(s2, i);
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
