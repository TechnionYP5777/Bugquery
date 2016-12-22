package com.bugquery.serverside.stacktrace;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * Created by yonatan on 29/11/16.
 */
public class JaccardSTDistancerTest extends TestCase {
    JaccardSTDistancer j;
    @Test
    public void setUp() throws Exception {
        this.j = new JaccardSTDistancer();
    }
    @Test
    public void testDistanceZeroForSameStrings() {
        String s = "aaaa\nbbbb\ncccc\n\n";
        assertEquals(0.0, this.j.distance(s, s));
        assertEquals(0.0, this.j.distance("", ""));
    }
    @Test
    public void testReturnNonZeroForDifferentStrings() {
        assertNotSame(0.0, this.j.distance("aaaa", "bbbb"));
        assertNotSame(0.0, this.j.distance("aaaa\nbbbb", "cccc\nbbbb"));
    }
    @Test
    public void testReturnsHigherDistanceForMoreDifferent() {
        // another 2-line with different values here
        assertTrue(this.j.distance("aaaa\nbbbb", "cccc\nbbbb") < this.j.distance("aaaa\nbbbb\n33", "cccc\nbbbb\n44"));
    }

}