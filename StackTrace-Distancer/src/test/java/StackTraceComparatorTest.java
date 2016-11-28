import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

/**
 * @author yonzarecki
 * @since 28.11.16
 */
public class StackTraceComparatorTest extends TestCase{
    @Test
    public void testSplitByNewlines_doesntReturnNull() {
        assertNotNull(StackTraceComparator.splitByNewlines(""));
        assertNotNull(StackTraceComparator.splitByNewlines(null));
        assertNotNull(StackTraceComparator.splitByNewlines("sdasdsada\nasdasdsa"));
    }
    @Test
    public void testSplitByNewlines_returnsEmptyListWhenNull() {
        assertTrue(StackTraceComparator.splitByNewlines(null).isEmpty());
    }
    @Test
    public void testSplitByNewlines_doesntReturnEmptyListWhenNotEmpty() {
        assertNotNull(StackTraceComparator.splitByNewlines("sdasdsada\nasdasdsa"));
        assertNotNull(StackTraceComparator.splitByNewlines("sdasdsada"));
    }
    @Test
    public void testSplitByNewlines_splitsCorrectly() {
        String exStackTrace = "Exception in thread \"main\" java.lang.NullPointerException\n" +
                "        at com.example.myproject.Book.getTitle(Book.java:16)\n" +
                "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" +
                "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n";
        List<String> st_split = StackTraceComparator.splitByNewlines(exStackTrace);
        assertEquals(4, st_split.size());
        assertEquals("Exception in thread \"main\" java.lang.NullPointerException", st_split.get(0));
        assertEquals("        at com.example.myproject.Book.getTitle(Book.java:16)", st_split.get(1));
        assertEquals("        at com.example.myproject.Author.getBookTitles(Author.java:25)", st_split.get(2));
        assertEquals("        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)", st_split.get(3));

    }

}