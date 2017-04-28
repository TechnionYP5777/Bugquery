package com.bugquery.serverside.stacktrace;

import junit.framework.TestCase;
import org.junit.Test;

import com.bugquery.serverside.stacktrace.distance.StackTraceDistancer;

import java.util.List;

/**
 * @author yonzarecki
 * @since 28.11.16
 */
@SuppressWarnings("static-method")
public class StackTraceDistancerTest extends TestCase{
    @Test
    public void testSplitByNewlines_doesntReturnNull() {
        assert StackTraceDistancer.splitByNewlines("") != null;
        assert StackTraceDistancer.splitByNewlines(null) != null;
        assert StackTraceDistancer.splitByNewlines("sdasdsada\nasdasdsa") != null;
    }
    @Test
    public void testSplitByNewlines_returnsEmptyListWhenNull() {
        assert StackTraceDistancer.splitByNewlines(null).isEmpty();
    }
    @Test
    public void testSplitByNewlines_doesntReturnEmptyListWhenNotEmpty() {
        assert StackTraceDistancer.splitByNewlines("sdasdsada\nasdasdsa") != null;
        assert StackTraceDistancer.splitByNewlines("sdasdsada") != null;
    }
    @Test
    public void testSplitByNewlines_splitsCorrectly() {
        List<String> st_split = StackTraceDistancer.splitByNewlines("Exception in thread \"main\" java.lang.NullPointerException\n"
				+ "        at com.example.myproject.Book.getTitle(Book.java:16)\n"
				+ "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
				+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n");
        assertEquals(4, st_split.size());
        assertEquals("Exception in thread \"main\" java.lang.NullPointerException", st_split.get(0));
        assertEquals("        at com.example.myproject.Book.getTitle(Book.java:16)", st_split.get(1));
        assertEquals("        at com.example.myproject.Author.getBookTitles(Author.java:25)", st_split.get(2));
        assertEquals("        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)", st_split.get(3));

    }

}