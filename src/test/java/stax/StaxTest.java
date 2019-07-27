package stax;

import org.junit.jupiter.api.Test;

import javax.xml.transform.stream.StreamSource;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StaxTest {

    @Test
    public void testParseWithIteratorApi() {
        // When
        List<Book> catalog = new StaxIteratorApi()
                .parse(new StreamSource(StaxTest.class.getResourceAsStream("/catalog.xml")));
        // Then
        assertEquals(3, catalog.size());
        assertEquals("Java and XML", catalog.get(0).getTitle());
        assertEquals("059610149X", catalog.get(0).getIsbn10());
    }

    @Test
    public void testParseWithCursorApi() {
        // When
        List<Book> catalog = new StaxCursorApi()
                .parse(new StreamSource(StaxTest.class.getResourceAsStream("/catalog.xml")));
        // Then
        assertEquals(3, catalog.size());
        assertEquals("Java and XML", catalog.get(0).getTitle());
        assertEquals("059610149X", catalog.get(0).getIsbn10());
    }

    @Test
    public void testWriteWithCursorApi() {
        // Given
        List<Book> catalog = Arrays.asList(
            createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        StringWriter sw = new StringWriter();
        new StaxCursorApi().write(catalog, sw);

        // Then
        assertEquals("<?xml version=\"1.0\" ?><catalog><book isbn10=\"059610149X\"><title>Java and XML</title></book><book isbn10=\"1590597060\"><title>Pro XML Development with Java Technology</title></book></catalog>", sw.toString());
    }

    @Test
    public void testWriteWithIteratorApi() {
        // Given
        List<Book> catalog = Arrays.asList(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        StringWriter sw = new StringWriter();
        new StaxIteratorApi().write(catalog, sw);

        // Then
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><catalog><book isbn10=\"059610149X\"><title>Java and XML</title></book><book isbn10=\"1590597060\"><title>Pro XML Development with Java Technology</title></book></catalog>", sw.toString());
    }

    private Book createBook(String title, String isbn10) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn10(isbn10);
        return book;
    }
}
