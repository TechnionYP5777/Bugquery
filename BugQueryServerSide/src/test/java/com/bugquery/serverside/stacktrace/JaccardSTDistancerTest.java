package com.bugquery.serverside.stacktrace;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * Created by yonatan on 29/11/16.
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
        assertEquals(new Double(0.0), new Double(this.j.distance(s, s)));
        assertEquals(new Double(0.0), new Double(this.j.distance("", "")));
    }
    @Test
    public void testReturnNonZeroForDifferentStrings() {
        assertNotSame(new Double(0.0), new Double(this.j.distance("aaaa", "bbbb")));
        assertNotSame(new Double(0.0), new Double(this.j.distance("aaaa\nbbbb", "cccc\nbbbb")));
    }
    @Test
    public void testReturnsHigherDistanceForMoreDifferent() {
        // another 2-line with different values here
        assertTrue(this.j.distance("aaaa\nbbbb", "cccc\nbbbb") < this.j.distance("aaaa\nbbbb\n33", "cccc\nbbbb\n44"));
    }

}