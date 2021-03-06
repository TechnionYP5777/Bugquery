package com.bugquery.serverside.stacktrace;

import org.junit.Test;

import com.bugquery.serverside.stacktrace.distance.JaccardSTDistancer;

import junit.framework.TestCase;

/**
 * @author yonzarecki
 * @since 29.11.16
 */
public class JaccardSTDistancerTest extends TestCase {
    JaccardSTDistancer j;
    @Override @Test
    public void setUp() {
        this.j = new JaccardSTDistancer();
    }
    @Test
    public void testDistanceZeroForSameStrings() {
        String s = "aaaa\nbbbb\ncccc\n\n";
        assertEquals(Double.valueOf(0.0), Double.valueOf(this.j.distance(s, s)));
        assertEquals(Double.valueOf(0.0), Double.valueOf(this.j.distance("", "")));
    }
    @Test
    public void testReturnNonZeroForDifferentStrings() {
        assertNotSame(Double.valueOf(0.0), Double.valueOf(this.j.distance("aaaa", "bbbb")));
        assertNotSame(Double.valueOf(0.0), Double.valueOf(this.j.distance("aaaa\nbbbb", "cccc\nbbbb")));
    }
    @Test
    public void testReturnsHigherDistanceForMoreDifferent() {
        assert this.j.distance("aaaa\nbbbb", "cccc\nbbbb") < this.j.distance("aaaa\nbbbb\n33",
				"cccc\nbbbb\n44") : "2-line with different values here";
    }

}