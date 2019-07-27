package sax;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaxTest {

    @Test
    public void testParse() {
        // When
        List<Book> books = new SaxApi()
                .parse(SaxTest.class.getResourceAsStream("/catalog.xml"));
        // Then
        assertEquals(3, books.size());
        assertEquals("Java and XML", books.get(0).getTitle());
        assertEquals("059610149X", books.get(0).getIsbn10());
    }

}
