package com.bugquery.serverside.stacktrace;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

/**
 * @author yonzarecki
 * @since 28.11.16
 */
public class StackTraceDistancerTest extends TestCase{
    @Test
    public void testSplitByNewlines_doesntReturnNull() {
        assertNotNull(StackTraceDistancer.splitByNewlines(""));
        assertNotNull(StackTraceDistancer.splitByNewlines(null));
        assertNotNull(StackTraceDistancer.splitByNewlines("sdasdsada\nasdasdsa"));
    }
    @Test
    public void testSplitByNewlines_returnsEmptyListWhenNull() {
        assertTrue(StackTraceDistancer.splitByNewlines(null).isEmpty());
    }
    @Test
    public void testSplitByNewlines_doesntReturnEmptyListWhenNotEmpty() {
        assertNotNull(StackTraceDistancer.splitByNewlines("sdasdsada\nasdasdsa"));
        assertNotNull(StackTraceDistancer.splitByNewlines("sdasdsada"));
    }
    @Test
    public void testSplitByNewlines_splitsCorrectly() {
        String exStackTrace = "Exception in thread \"main\" java.lang.NullPointerException\n" +
                "        at com.example.myproject.Book.getTitle(Book.java:16)\n" +
                "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" +
                "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n";
        List<String> st_split = StackTraceDistancer.splitByNewlines(exStackTrace);
        assertEquals(4, st_split.size());
        assertEquals("Exception in thread \"main\" java.lang.NullPointerException", st_split.get(0));
        assertEquals("        at com.example.myproject.Book.getTitle(Book.java:16)", st_split.get(1));
        assertEquals("        at com.example.myproject.Author.getBookTitles(Author.java:25)", st_split.get(2));
        assertEquals("        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)", st_split.get(3));

    }

}